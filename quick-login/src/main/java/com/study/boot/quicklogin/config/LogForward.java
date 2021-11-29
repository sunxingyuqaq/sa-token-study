package com.study.boot.quicklogin.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.core.task.TaskExecutor;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

/**
 * describe:
 *
 * @author admin
 * @date 2021/11/23 09:54
 */
@Component
public class LogForward {

    protected final Logger logger = LoggerFactory.getLogger(this.getClass());
    /**
     * 这个类是Spring提供用来发送消息的类
     */
    @Autowired
    private SimpMessagingTemplate messagingTemplate;
    /**
     * 线程池，没有的话可以直接new Thread
     */
    @Autowired
    @Qualifier("myExecutor")
    private TaskExecutor taskExecutor;

    /**
     * 获取阻塞队列的实例
     */
    private final LoggerQueue loggerQueue = LoggerQueue.getInstance();

    /**
     * 每30s打印一条日志用以测试
     */
    @Scheduled(cron = "*/30 * * * * *")
    @Async(value = "myExecutor")
    public void ok() {
        logger.info("ok");
    }

    /**
     * 每50s打印一条日志用以测试
     */
    @Scheduled(cron = "*/50 * * * * *")
    @Async(value = "myExecutor")
    public void test() {
        logger.info("test");
    }

    @PostConstruct
    public void pushLogs() {
        taskExecutor.execute(() -> {
            // 从队列中读取日志并发送
            while (true) {
                String message = loggerQueue.pop();
                messagingTemplate.convertAndSend("/topic/logs", message);
            }
        });
    }
}
