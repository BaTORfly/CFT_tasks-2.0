package ru.batorfly.producer;

import lombok.Getter;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import ru.batorfly.resource.Resource;
import ru.batorfly.storage.Storage;

import java.util.concurrent.atomic.AtomicInteger;

@Getter
public class ResourceProducingTask implements Runnable {
    private static final Logger log = LogManager.getLogger(ResourceProducingTask.class);
    private static final AtomicInteger counter = new AtomicInteger(1);

    private final int producerTimeMillis;
    private final int producerId;
    private final Storage storage;

    public ResourceProducingTask(int producerTimeMillis, Storage storage) {
        this.producerTimeMillis = producerTimeMillis;
        synchronized (ResourceProducingTask.class) {
            this.producerId = counter.getAndIncrement();
        }
        this.storage = storage;
    }

    @Override
    public void run() {
        log.info("Producer #{} started producing", producerId);
        try {
            while (!Thread.currentThread().isInterrupted()) {
                Thread.sleep(this.producerTimeMillis);
                this.storage.produce(new Resource(), producerId);
            }
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            log.info("Producer #'{}' is interrupted", producerId);
        }
    }

}
