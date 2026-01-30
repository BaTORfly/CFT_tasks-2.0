package ru.batorfly.consumer;

import lombok.Getter;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import ru.batorfly.resource.Resource;
import ru.batorfly.storage.Storage;

import java.util.Optional;
import java.util.concurrent.atomic.AtomicInteger;

public class ResourceConsumingTask implements Runnable {
    private static final Logger log = LogManager.getLogger(ResourceConsumingTask.class);
    private static final AtomicInteger idCounter = new AtomicInteger(1);

    private final int consumerTimeMillis;
    @Getter
    private final int consumerId;
    private final Storage storage;

    public ResourceConsumingTask(final int consumerTimeMillis, Storage storage) {
        this.consumerTimeMillis = consumerTimeMillis;
        synchronized (ResourceConsumingTask.class) {
            this.consumerId = idCounter.getAndIncrement();
        }
        this.storage = storage;
    }

    @Override
    public void run() {
        log.info("Consumer #{} start working", this.consumerId);
        try{
            while (!Thread.currentThread().isInterrupted()){
                Thread.sleep(consumerTimeMillis);
                final Optional<Resource> optionalConsumedResource = this.storage.consume(this.consumerId);
                optionalConsumedResource.orElseThrow(ResourceConsumingException::new);
            }
        } catch (InterruptedException ex){
            Thread.currentThread().interrupt();
        }
    }
}
