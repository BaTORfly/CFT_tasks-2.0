package parameters;

import com.beust.jcommander.Parameter;
import lombok.Getter;
import parameters.validators.InputFileValidator;

import java.util.ArrayList;
import java.util.List;

@Getter
public class DataFiltersParams {
    @Parameter(description = "Input files for further filtering", validateWith = InputFileValidator.class)
    private List<String> inputFiles = new ArrayList<>();
    @Parameter(names = "-o", description = "Output path for filtered files")
    private String outputPath = "./output";
    @Parameter(names = "-p", description = "prefix for filtered files")
    private String prefix;
    @Parameter(names = "-a", description = "append mode")
    private boolean appendMode = false;
    @Parameter(names = "-s", description = "short statistics")
    private boolean shortStats = false;
    @Parameter(names = "-f", description = "full statistics")
    private boolean fullStats = false;
}
