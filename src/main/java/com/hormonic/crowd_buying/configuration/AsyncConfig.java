package com.hormonic.crowd_buying.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
//import org.springframework.core.task.AsyncTaskExecutor;
import org.springframework.core.task.TaskExecutor;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

@Configuration
public class AsyncConfig {

    @Bean
    public TaskExecutor asyncTaskExecutor() {
        ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();  // 비동기 작업 실행자
        executor.setCorePoolSize(2);  // 최소 스레드 수
        executor.setMaxPoolSize(5);  // 최대 스레드 수
        executor.setQueueCapacity(500);  // 작업 큐 최대 크기
        executor.setThreadNamePrefix("Async-");
        executor.initialize();

        return executor;
    }
}
