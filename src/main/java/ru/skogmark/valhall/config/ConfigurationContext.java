package ru.skogmark.valhall.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import ru.skogmark.common.config.ConfigurationLoader;

@Configuration
public class ConfigurationContext {
    @Bean
    ApplicationConfiguration applicationConfiguration(ConfigurationLoader configurationLoader) {
        return configurationLoader.loadConfiguration(ApplicationConfiguration.class, "app.cfg.xml");
    }

    @Bean
    DataSourceConfiguration datasourceConfiguration(ConfigurationLoader configurationLoader) {
        return configurationLoader.loadConfiguration(DataSourceConfiguration.class, "dataSource.cfg.xml");
    }
}
