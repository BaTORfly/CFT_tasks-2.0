package ru.batorfly.parameters;

import lombok.Getter;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.core.tools.picocli.CommandLine;
import ru.batorfly.parameters.utils.ValidationUtils;

@CommandLine.Command(
        name = "ShapeProcessor",
        description = "Processing shape information from input file",
        version = "1.0"
)
public class ParseCmdParams implements Runnable{
    private static final Logger log = LogManager.getLogger(ParseCmdParams.class);

    @CommandLine.Parameters(
            index = "0",
            description = "Input file for further data reading",
            paramLabel = "INPUT_FILE"
    )
    private String inputFile;

    @picocli.CommandLine.ArgGroup(exclusive = true, multiplicity = "1")
    private OutputMode outputMode = new OutputMode();

    static class OutputMode {
        @CommandLine.Option(
                names = "-c",
                description = "Output of the result (shape information) to the console"
        )
        private boolean consoleMode;

        @CommandLine.Option(
                names = "-f",
                description = "output of the result (shape information) to the file"
        )
        private boolean fileMode;
    }

    @Getter
    private CmdParams parsedCmdParams;

    @Override
    public void run() {
        String resolvedInputPath = ValidationUtils.validateAndResolveInputFile(inputFile);
        parsedCmdParams = new CmdParams(resolvedInputPath, outputMode.consoleMode, outputMode.fileMode);
    }

    public static CmdParams parse(String[] args){
        ParseCmdParams parser = new ParseCmdParams();
        var cmd = new picocli.CommandLine(parser);

        try {
            int exitCode = cmd.execute(args);

            if (exitCode != 0) System.exit(exitCode);

            return parser.getParsedCmdParams();
        } catch (CommandLine.ParameterException ex) {
            log.error("Command line error: " + ex.getMessage());
            cmd.usage(System.err);
            System.exit(1);
            return null;
        } catch (IllegalArgumentException ex) {
            log.error("Validation error: " + ex.getMessage());
            cmd.usage(System.err);
            System.exit(1);
            return null;
        }
    }
}
