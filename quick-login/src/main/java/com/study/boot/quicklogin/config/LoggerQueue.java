package com.study.boot.quicklogin.config;

import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

/**
 * describe:
 *
 * @author admin
 * @date 2021/11/23 09:47
 */
@Slf4j
public class LoggerQueue {

    public static final int QUEUE_MAX_SIZE = Integer.MAX_VALUE;
    private static final LoggerQueue ALARM_MESSAGE_QUEUE = new LoggerQueue();
    private final BlockingQueue<String> queue = new LinkedBlockingQueue<>(QUEUE_MAX_SIZE);

    public static LoggerQueue getInstance() {
        return ALARM_MESSAGE_QUEUE;
    }

    public boolean push(String log) {
        return this.queue.add(log);
    }

    public String pop() {
        String result = null;
        try {
            result = this.queue.take();
        }
        catch (InterruptedException e) {
            log.error("pop error", e);
        }
        return result;
    }
}
