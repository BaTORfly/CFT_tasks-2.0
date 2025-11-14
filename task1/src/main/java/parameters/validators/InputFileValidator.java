package parameters.validators;

import com.beust.jcommander.IParameterValidator;
import com.beust.jcommander.ParameterException;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class InputFileValidator implements IParameterValidator {
    @Override
    public void validate(String name, String value) throws ParameterException {
        Path path = Paths.get(value).toAbsolutePath().normalize();

        if (!Files.exists(path)) {
            throw new ParameterException("Input file " + value + "in path: " + path + " does not exist");
        }
        if (!Files.isRegularFile(path)) {
            throw new ParameterException("Input file " + value + " is not a file");
        }
        if (!Files.isReadable(path)) {
            throw new ParameterException("Input file " + value + " is not readable");
        }
        if (!value.toLowerCase().endsWith(".txt")){
            throw new ParameterException("Parameter " + name + " is not a text file." +
                    value + "is not valid");
        }
    }
}
