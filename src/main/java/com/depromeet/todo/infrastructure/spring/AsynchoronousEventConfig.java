package com.depromeet.todo.infrastructure.spring;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.event.ApplicationEventMulticaster;
import org.springframework.context.event.SimpleApplicationEventMulticaster;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

@Configuration
public class AsynchoronousEventConfig {
    @Bean
    public ApplicationEventMulticaster applicationEventMulticaster() {
        SimpleApplicationEventMulticaster applicationEventMulticaster = new SimpleApplicationEventMulticaster();
        applicationEventMulticaster.setTaskExecutor(this.asyncEventTaskExecutor());
        return applicationEventMulticaster;
    }

    @Bean
    public ThreadPoolTaskExecutor asyncEventTaskExecutor() {
        ThreadPoolTaskExecutor threadPoolTaskExecutor = new ThreadPoolTaskExecutor();
        threadPoolTaskExecutor.setMaxPoolSize(10);
        threadPoolTaskExecutor.setCorePoolSize(10);
        threadPoolTaskExecutor.setQueueCapacity(1000);
        threadPoolTaskExecutor.setThreadNamePrefix("asyncEvent-");
        threadPoolTaskExecutor.setAwaitTerminationSeconds(30);
        threadPoolTaskExecutor.setWaitForTasksToCompleteOnShutdown(true);
        return threadPoolTaskExecutor;
    }
}
