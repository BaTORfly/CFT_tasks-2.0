package ru.batorfly.storage;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import ru.batorfly.resource.Resource;

import java.util.ArrayDeque;
import java.util.Optional;
import java.util.Queue;

public final class Storage {
    private static final Logger log = LogManager.getLogger(Storage.class);

    private static final String MESSAGE_PRODUCED_TEMPLATE = "Resource #'{}' is produced by producer #'{}'" +
            "Amount of resources before producing = {}.";

    private static final String MESSAGE_CONSUMED_TEMPLATE = "Resource #'{}' is consumed by consumer #'{}'" +
            "Amount of messages before consuming = {}.";

    private static final String MESSAGE_PRODUCER_WAIT_TEMPLATE = "Producer #{} waiting space in storage";
    private static final String MESSAGE_PRODUCER_NOTIFY_TEMPLATE = "Producer #{} return to producing resources";

    private static final String MESSAGE_CONSUMER_WAIT_TEMPLATE = "Consumer #{} waiting for resources to appear in " +
            "the storage";
    private static final String MESSAGE_CONSUMER_NOTIFY_TEMPLATE = "Consumer #{} return to consuming resources";


    private final int capacity;

    private final Queue<Resource> resourcesToBeConsumed;

    public Storage(int capacity) {
        this.capacity = capacity;
        resourcesToBeConsumed = new ArrayDeque<>(capacity);
    }

    public synchronized void produce(Resource resource, int producerId) {
        try {
            while (this.resourcesToBeConsumed.size() >= this.capacity) {
                log.info(MESSAGE_PRODUCER_WAIT_TEMPLATE, producerId);
                super.wait();
                log.info(MESSAGE_PRODUCER_NOTIFY_TEMPLATE, producerId);
            }
            log.info(MESSAGE_PRODUCED_TEMPLATE, resource.getId(), producerId,
                    this.resourcesToBeConsumed.size());
            this.resourcesToBeConsumed.add(resource);
            super.notify();
        } catch (InterruptedException ex) {
            Thread.currentThread().interrupt();
            log.info("Thread '{}' was interrupted", Thread.currentThread().getName());
        }
    }

    public synchronized Optional<Resource> consume(int consumerId){
        try{
            while(this.resourcesToBeConsumed.isEmpty()){
                log.info(MESSAGE_CONSUMER_WAIT_TEMPLATE, consumerId);
                super.wait();
                log.info(MESSAGE_CONSUMER_NOTIFY_TEMPLATE, consumerId);
            }
            Resource consumedResource = this.resourcesToBeConsumed.poll();
            log.info(MESSAGE_CONSUMED_TEMPLATE, consumedResource.getId(), consumerId,
                    this.resourcesToBeConsumed.size() + 1);
            super.notify();
            return Optional.ofNullable(consumedResource);
        } catch (InterruptedException ex) {
            Thread.currentThread().interrupt();
            log.info("Consumer #{} was interrupted", consumerId);
            return Optional.empty();
        }
    }

}
