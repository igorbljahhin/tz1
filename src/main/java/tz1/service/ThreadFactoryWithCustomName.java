package tz1.service;

import java.util.concurrent.ThreadFactory;

public class ThreadFactoryWithCustomName implements ThreadFactory {
    private final ThreadFactory threadFactory;
    private final String name;

    public ThreadFactoryWithCustomName(final ThreadFactory threadFactory, final String name) {
        this.threadFactory = threadFactory;
        this.name = name;
    }

    @Override
    public Thread newThread(final Runnable r) {
        final Thread thread = threadFactory.newThread(r);
        thread.setName(name);

        return thread;
    }
}
