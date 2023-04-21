package com.lhstack.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebMvcConfiguration implements WebMvcConfigurer{

    @Override
    public void configureAsyncSupport(org.springframework.web.servlet.config.annotation.AsyncSupportConfigurer configurer) {
        ThreadPoolTaskExecutor threadPoolTaskExecutor = new ThreadPoolTaskExecutor();
        threadPoolTaskExecutor.setCorePoolSize(200);
        threadPoolTaskExecutor.setMaxPoolSize(300);
        threadPoolTaskExecutor.setDaemon(true);
        threadPoolTaskExecutor.setQueueCapacity(256);
        threadPoolTaskExecutor.afterPropertiesSet();
        configurer.setDefaultTimeout(60000);
        configurer.setTaskExecutor(threadPoolTaskExecutor);
    }
}