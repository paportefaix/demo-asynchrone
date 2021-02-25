package com.example.demo.asynchrone.config;

import lombok.NoArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import java.util.concurrent.Executor;


@Configuration
@NoArgsConstructor
public class AsyncConfiguration {

    private final int    corePoolSize     = 12;
    private final int    maxPoolSize      = 24;
    private final int    queueCapacity    = 42;
    private final String threadNamePrefix = "Demo-async";

    @Bean
    public Executor asyncExecutor() {

        final ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
        executor.setCorePoolSize(corePoolSize);
        executor.setMaxPoolSize(maxPoolSize);
        executor.setQueueCapacity(queueCapacity);
        executor.setThreadNamePrefix(threadNamePrefix);
        executor.initialize();
        return executor;
    }
}
