package ru.skogmark.valhall.core.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import ru.skogmark.common.config.ConfigurationLoader;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;

@Configuration
public class CoreContext {
    @Bean
    ScheduledExecutorService subjectWorkerExecutor() {
        return Executors.newSingleThreadScheduledExecutor(runnable -> new Thread(runnable, "subject-worker"));
    }

    @Bean
    SubjectsConfiguration subjectsConfiguration(ConfigurationLoader configurationLoader) {
        return configurationLoader.loadConfiguration(SubjectsConfiguration.class, "subjects.cfg.xml");
    }
}
