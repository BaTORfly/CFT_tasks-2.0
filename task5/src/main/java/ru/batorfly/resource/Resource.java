package ru.batorfly.resource;

import lombok.Getter;

import java.util.concurrent.atomic.AtomicInteger;

@Getter
public final class Resource {
    private static final AtomicInteger idCounter = new AtomicInteger(0);

    private final int id;

    public Resource() {
        synchronized (Resource.class){
            this.id = idCounter.incrementAndGet();
        }
    }
}
