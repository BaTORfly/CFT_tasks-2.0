package ru.batorfly.config;

import lombok.AccessLevel;
import lombok.Getter;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import ru.batorfly.config.exceptions.ConfigLoadingException;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;
/**
 * producerCount=3
 * consumerCount=3
 * producerTime=1
 * consumerTime=1
 * storageSize=15
 */
@Getter
public final class Config {
    private static final Logger log = LogManager.getLogger(Config.class);
    private static final String CONFIG_FILE = "config.properties";
    private static final String PRODUCER_COUNT_KEY = "producerCount";
    private static final String CONSUMER_COUNT_KEY = "consumerCount";
    private static final String STORAGE_SIZE_KEY = "storageSize";
    private static final String PRODUCER_TIME_KEY = "producerTime";
    private static final String CONSUMER_TIME_KEY = "consumerTime";


    private static final int DEFAULT_PRODUCER_THREADS = 1;
    private static final int DEFAULT_CONSUMER_THREADS = 1;
    private static final int DEFAULT_STORAGE_SIZE = 10;
    private static final int DEFAULT_PRODUCER_TIME = 1000;
    private static final int DEFAULT_CONSUMER_TIME = 1000;


    @Getter(AccessLevel.NONE)
    private final Properties props;

    // cached props
    private int producerCount;
    private int consumerCount;
    private int storageSize;
    private int producerTime;
    private int consumerTime;

    public Config() throws ConfigLoadingException{
        this.props = new Properties();
        loadPropertiesFromConfigFile();
        validateAndInitProperties();
    }

    private void loadPropertiesFromConfigFile() throws ConfigLoadingException {
        try (InputStream input = getClass().getClassLoader().getResourceAsStream(CONFIG_FILE)) {
            log.info("Start loading properties from file: {}", CONFIG_FILE);
            if (input == null) {
                throw new ConfigLoadingException("Config file '" + CONFIG_FILE + "' not found in resources");
            }
            props.load(input);
        } catch (IOException ex) {
            throw new ConfigLoadingException("Couldn't load configuration file '" + CONFIG_FILE + "'", ex);
        }
    }

    private void validateAndInitProperties() {
        this.producerCount = validateAndInitInt(PRODUCER_COUNT_KEY, DEFAULT_PRODUCER_THREADS);
        this.consumerCount = validateAndInitInt(CONSUMER_COUNT_KEY, DEFAULT_CONSUMER_THREADS);
        this.storageSize = validateAndInitInt(STORAGE_SIZE_KEY, DEFAULT_STORAGE_SIZE);
        this.producerTime = validateAndInitInt(PRODUCER_TIME_KEY, DEFAULT_PRODUCER_TIME);
        this.consumerTime = validateAndInitInt(CONSUMER_TIME_KEY, DEFAULT_CONSUMER_TIME);
    }

    private Integer validateAndInitInt(String key, int defaultValue) {
        try{
            return Integer.parseInt(props.getProperty(key));
        } catch (NumberFormatException ex) {
            log.warn("Property '{}' isn't set. Use default value: {}", key, defaultValue);
            return defaultValue;
        }
    }

    @Override
    public String toString() {
        return "Config{" +
                "props=" + props +
                '}';
    }
}
