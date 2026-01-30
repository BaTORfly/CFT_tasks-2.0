package ru.batorfly;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import ru.batorfly.config.Config;
import ru.batorfly.config.exceptions.ConfigLoadingException;
import ru.batorfly.consumer.ResourceConsumingTask;
import ru.batorfly.producer.ResourceProducingTask;
import ru.batorfly.storage.Storage;

public class Main {
    private static final Logger log = LogManager.getLogger(Main.class);

    public static void main(String[] args) {
        Config config = null;
        try {
            config = new Config();
            System.out.println(config.toString());
        } catch (ConfigLoadingException ex) {
            log.error("Error while loading configuration", ex);
            System.exit(1);
        }

        var storage = new Storage(config.getStorageSize());

        for (int i = 0; i < config.getProducerCount(); i++) {
            var resourceProducingTask = new ResourceProducingTask(config.getProducerTime(), storage);
            new Thread(resourceProducingTask).start();
        }

        for (int i = 0; i < config.getConsumerCount(); i++) {
            var resourceConsumingTask = new ResourceConsumingTask(config.getConsumerTime(), storage);
            new Thread(resourceConsumingTask).start();
        }
    }
}