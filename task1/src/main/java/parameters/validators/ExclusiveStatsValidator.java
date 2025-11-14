package parameters.validators;

import com.beust.jcommander.IParameterValidator;
import com.beust.jcommander.ParameterException;

public class ExclusiveStatsValidator implements IParameterValidator {
    @Override
    public void validate(String s1, String s2) throws ParameterException {

    }

    public static void validateStats(boolean shortStats, boolean fullStats) throws ParameterException {
        if (shortStats && fullStats) {
            throw new ParameterException("You can't use mutually exclusive parameters " +
                    "(--full-stats and --short-stats)");
        }
    }
}
