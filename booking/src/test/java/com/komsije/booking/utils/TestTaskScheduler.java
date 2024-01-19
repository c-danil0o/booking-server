package com.komsije.booking.utils;

import org.springframework.scheduling.TaskScheduler;
import org.springframework.scheduling.Trigger;

import java.time.Duration;
import java.time.Instant;
import java.util.concurrent.ScheduledFuture;

public class TestTaskScheduler implements TaskScheduler {
    @Override
    public ScheduledFuture<?> schedule(Runnable task, Trigger trigger) {
        return null;
    }

    @Override
    public ScheduledFuture<?> schedule(Runnable task, Instant startTime) {
        if (startTime.isBefore(Instant.now()))
            task.run();
        return null;
    }

    @Override
    public ScheduledFuture<?> scheduleAtFixedRate(Runnable task, Instant startTime, Duration period) {
        return null;
    }

    @Override
    public ScheduledFuture<?> scheduleAtFixedRate(Runnable task, Duration period) {
        return null;
    }

    @Override
    public ScheduledFuture<?> scheduleWithFixedDelay(Runnable task, Instant startTime, Duration delay) {
        return null;
    }

    @Override
    public ScheduledFuture<?> scheduleWithFixedDelay(Runnable task, Duration delay) {
        return null;
    }
}
