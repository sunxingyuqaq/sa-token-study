package com.study.boot.quicklogin;

import cn.dev33.satoken.quick.SaQuickManager;
import cn.hutool.core.lang.Console;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.core.task.TaskExecutor;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.server.standard.ServerEndpointExporter;

import java.time.LocalDateTime;
import java.util.concurrent.ThreadPoolExecutor;


/**
 * @author sxy
 */
@Slf4j
@EnableScheduling
@EnableAsync
@EnableWebSocketMessageBroker
@ComponentScan(basePackages = "com.study.boot")
@SpringBootApplication
public class QuickLoginApplication {

    @Value("${async.executor.thread.core_pool_size}")
    private int corePoolSize;
    @Value("${async.executor.thread.max_pool_size}")
    private int maxPoolSize;
    @Value("${async.executor.thread.queue_capacity}")
    private int queueCapacity;
    @Value("${async.executor.thread.name.prefix}")
    private String namePrefix;
    @Value("${async.executor.thread.keep_alive_seconds}")
    private int keepAliveSeconds;

    @Bean("myExecutor")
    public TaskExecutor taskExecutor() {
        ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
        // 设置核心线程数
        executor.setCorePoolSize(corePoolSize);
        // 设置最大线程数
        executor.setMaxPoolSize(maxPoolSize);
        // 设置队列容量
        executor.setQueueCapacity(queueCapacity);
        // 设置线程活跃时间（秒）
        executor.setKeepAliveSeconds(keepAliveSeconds);
        // 设置默认线程名称
        executor.setThreadNamePrefix(namePrefix);
        // 设置拒绝策略
        executor.setRejectedExecutionHandler(new ThreadPoolExecutor.CallerRunsPolicy());
        // 等待所有任务结束后再关闭线程池
        executor.setWaitForTasksToCompleteOnShutdown(true);
        log.info("创建一个线程池 corePoolSize is [" + corePoolSize + "] maxPoolSize is [" + maxPoolSize + "] queueCapacity is [" + queueCapacity +
                "] keepAliveSeconds is [" + keepAliveSeconds + "] namePrefix is [" + namePrefix + "].");
        log.error("start application error log ok " + LocalDateTime.now());
        log.warn("start application warn log ok " + LocalDateTime.now());
        return executor;
    }

    @Bean
    public ServerEndpointExporter serverEndpointExporter() {
        return new ServerEndpointExporter();
    }

    public static void main(String[] args) {
        SpringApplication.run(QuickLoginApplication.class, args);
        Console.log("\n------ 启动成功 ------");
        Console.log("name: " + SaQuickManager.getConfig().getName());
        Console.log("pwd:  " + SaQuickManager.getConfig().getPwd());
    }

}
