package ru.skogmark.valhall;

import com.google.common.collect.Sets;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Profile;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.support.TransactionTemplate;
import ru.skogmark.common.config.ConfigurationLoader;
import ru.skogmark.common.config.Configurations;
import ru.skogmark.common.migration.MigrationService;
import ru.skogmark.valhall.config.ApplicationConfiguration;
import ru.skogmark.valhall.config.DataSourceConfiguration;

import javax.sql.DataSource;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@SpringBootApplication
public class ValhallApplication {
    public static final String PROFILE_PRODUCTION = "production";

    public static void main(String[] args) {
        SpringApplication.run(ValhallApplication.class, args);
    }

    @Bean
    DataSource postgresDataSource(DataSourceConfiguration dataSourceConfiguration) {
        DriverManagerDataSource dataSource = new DriverManagerDataSource();
        dataSource.setDriverClassName("org.postgresql.Driver");
        dataSource.setUrl(dataSourceConfiguration.getUrl());
        dataSource.setUsername(dataSourceConfiguration.getUsername());
        dataSource.setPassword(dataSourceConfiguration.getPassword());
        return dataSource;
    }

    @Bean
    PlatformTransactionManager postgresDataSourceTransactionManager(
            @Qualifier("postgresDataSource") DataSource dataSource) {
        return new DataSourceTransactionManager(dataSource);
    }

    @Bean
    TransactionTemplate postgresTransactionTemplate(
            @Qualifier("postgresDataSourceTransactionManager") PlatformTransactionManager transactionManager) {
        return new TransactionTemplate(transactionManager);
    }

    @Bean
    NamedParameterJdbcTemplate postgresNamedParameterJdbcTemplate(
            @Qualifier("postgresDataSource") DataSource dataSource) {
        return new NamedParameterJdbcTemplate(dataSource);
    }

    @Bean
    @Profile(PROFILE_PRODUCTION)
    MigrationService migrationService(@Qualifier("postgresTransactionTemplate") TransactionTemplate transactionTemplate,
                                      @Qualifier("postgresNamedParameterJdbcTemplate") NamedParameterJdbcTemplate
                                              namedParameterJdbcTemplate) {
        return new MigrationService(transactionTemplate, namedParameterJdbcTemplate,
                Sets.newHashSet("premoderation-queue.table.sql"));
    }

    @Bean
    ConfigurationLoader configurationLoader() {
        return Configurations.newXmlConfigurationLoader();
    }

    @Bean
    ExecutorService outputRequestExecutor(ApplicationConfiguration applicationConfiguration) {
        return Executors.newFixedThreadPool(applicationConfiguration.getOutputRequestThreadPoolSize());
    }
}
