import com.beust.jcommander.JCommander;
import com.beust.jcommander.ParameterException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import parameters.DataFiltersParams;
import parameters.validators.ExclusiveStatsValidator;
import services.DataFilterProcessor;

public class Main {
    private static final Logger log = LogManager.getLogger(Main.class);

    public static void main(String[] args) {
        log.info("The program is running...");
        DataFiltersParams params = new DataFiltersParams();
        JCommander jc = JCommander.newBuilder().addObject(params).build();
        try{
            jc.parse(args);
            ExclusiveStatsValidator.validateStats(params.isShortStats(), params.isFullStats());
        } catch (ParameterException e) {
            log.error(e.getMessage());
            jc.usage();
        }
        DataFilterProcessor dataFilterProcessor = new DataFilterProcessor(params);
        dataFilterProcessor.run();
        log.info("The program is finished.");
    }
}
