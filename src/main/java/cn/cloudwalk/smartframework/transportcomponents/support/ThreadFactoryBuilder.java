package cn.cloudwalk.smartframework.transportcomponents.support;

import java.util.concurrent.Executors;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.atomic.AtomicLong;

/**
 * ThreadFactoryBuilder
 *
 * @since 1.0.0
 * @author liyanhui
 */
public final class ThreadFactoryBuilder {

    private String nameFormat = null;
    private Boolean daemon = null;
    private Integer priority = null;
    private Thread.UncaughtExceptionHandler uncaughtExceptionHandler = null;
    private ThreadFactory backingThreadFactory = null;

    public ThreadFactoryBuilder() {
    }

    public ThreadFactoryBuilder setNameFormat(String nameFormat) {
        String.format(nameFormat, 0);
        this.nameFormat = nameFormat;
        return this;
    }

    public ThreadFactoryBuilder setDaemon(boolean daemon) {
        this.daemon = daemon;
        return this;
    }

    public ThreadFactoryBuilder setPriority(int priority) {
        this.priority = priority;
        return this;
    }

    public ThreadFactoryBuilder setUncaughtExceptionHandler(Thread.UncaughtExceptionHandler uncaughtExceptionHandler) {
        this.uncaughtExceptionHandler = uncaughtExceptionHandler;
        return this;
    }

    public ThreadFactoryBuilder setThreadFactory(ThreadFactory backingThreadFactory) {
        this.backingThreadFactory = backingThreadFactory;
        return this;
    }

    public ThreadFactory build() {
        return build(this);
    }

    private static ThreadFactory build(ThreadFactoryBuilder builder) {
        final String nameFormat = builder.nameFormat;
        final Boolean daemon = builder.daemon;
        final Integer priority = builder.priority;
        final Thread.UncaughtExceptionHandler uncaughtExceptionHandler = builder.uncaughtExceptionHandler;
        final ThreadFactory backingThreadFactory = builder.backingThreadFactory != null ? builder.backingThreadFactory : Executors.defaultThreadFactory();
        final AtomicLong count = nameFormat != null ? new AtomicLong(0L) : null;
        return runnable -> {
            Thread thread = backingThreadFactory.newThread(runnable);
            if (nameFormat != null) {
                thread.setName(String.format(nameFormat, count.getAndIncrement()));
            }

            if (daemon != null) {
                thread.setDaemon(daemon);
            }

            if (priority != null) {
                thread.setPriority(priority);
            }

            if (uncaughtExceptionHandler != null) {
                thread.setUncaughtExceptionHandler(uncaughtExceptionHandler);
            }

            return thread;
        };
    }
}
