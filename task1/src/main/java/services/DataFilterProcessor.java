package services;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import parameters.DataFiltersParams;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Paths;

public class DataFilterProcessor {
    private static final Logger log = LogManager.getLogger(DataFilterProcessor.class);
    private final DataFiltersParams params;
    private final StatisticsCollector statisticsCollector = StatisticsCollector.INSTANCE;

    public DataFilterProcessor(DataFiltersParams params) {
        this.params = params;
        log.debug("The DataFilterProcessor is initialized with parameters: outputPath={}, prefix={}, " +
                "appendMode={}, shortStats={}, fullStats={}",
                params.getOutputPath(), params.getPrefix(), params.isAppendMode(),
                params.isShortStats(), params.isFullStats());
    }

    public void run() {
        try {
            process();
            statisticsCollector.printStatistics(params.isShortStats(), params.isFullStats());
        } catch (IOException ex) {
            log.error(ex.getMessage(), ex);
        }
    }
    private void process() throws IOException {
        log.info("The beginning of data processing. Number of data files = {}", params.getInputFiles().size());

        FileWriter intWriter = null, doubleWriter = null, stringWriter = null;

        for (String inputFile : params.getInputFiles()) {
            try (BufferedReader br = new BufferedReader(new FileReader(inputFile))) {
                String line;
                while ((line = br.readLine()) != null) {
                    if (isInteger(line)) {
                        if(intWriter == null) {
                            intWriter = getFileWriter(params.getOutputPath(), "integers.txt");
                        }
                        Long intVal = Long.parseLong(line);
                        intWriter.write(intVal + "\n");
                        statisticsCollector.updateIntStats(intVal);
                    } else if (isDouble(line)) {
                        if(doubleWriter == null) {
                            doubleWriter = getFileWriter(params.getOutputPath(), "doubles.txt");
                        }
                        Double doubleVal = Double.parseDouble(line);
                        doubleWriter.write(doubleVal + "\n");
                        statisticsCollector.updateDoubleStats(doubleVal);
                    } else {
                        if (stringWriter == null) {
                            stringWriter = getFileWriter(params.getOutputPath(), "strings.txt");
                        }
                        stringWriter.write(line + "\n");
                        statisticsCollector.updateStringStats(line);
                    }
                }
            } catch (IOException ex) {
                throw new IOException("Error while processing file: " + inputFile, ex);
            }
        }
        if (intWriter != null) intWriter.close();
        if (doubleWriter != null) doubleWriter.close();
        if (stringWriter != null) stringWriter.close();
        log.info("The end of data processing. Number of files = {}", params.getInputFiles().size());
    }

    /**
     * Support method for lazy FileWriter creation
     * @param outputPath
     * @param fileName
     * @return
     * @throws IOException
     */
    private FileWriter getFileWriter(String outputPath, String fileName) throws IOException {
        String fullPath = Paths.get(outputPath, params.getPrefix() + fileName).toString();
        return new FileWriter(fullPath, params.isAppendMode());
    }

    private boolean isInteger (String line) {
        try {
            Long.parseLong(line);
            return true;
        } catch (NumberFormatException ex){
            return false;
        }
    }

    private boolean isDouble (String line) {
        try {
            Double.parseDouble(line);
            return line.contains(".");
        } catch (NumberFormatException ex){
            return false;
        }
    }
}
