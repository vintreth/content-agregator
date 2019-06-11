package ru.skogmark.aggregator;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jdk8.Jdk8Module;
import com.google.common.collect.Sets;
import org.apache.http.client.HttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
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
import ru.skogmark.aggregator.prop.AggregatorProperties;
import ru.skogmark.aggregator.prop.DataSourceProperties;
import ru.skogmark.common.config.ConfigurationLoader;
import ru.skogmark.common.config.Configurations;
import ru.skogmark.common.migration.MigrationService;
import ru.skogmark.framework.request.OutgoingRequestService;

import javax.sql.DataSource;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.atomic.AtomicInteger;

@SpringBootApplication
public class AggregatorApplication {
    public static final String PROFILE_PRODUCTION = "production";
    public static final String PROFILE_TEST = "test";

    public static void main(String[] args) {
        SpringApplication.run(AggregatorApplication.class, args);
    }

    @Bean
    DataSource postgresDataSource(DataSourceProperties dataSourceProperties) {
        DriverManagerDataSource dataSource = new DriverManagerDataSource();
        dataSource.setDriverClassName("org.postgresql.Driver");
        dataSource.setUrl(dataSourceProperties.getUrl());
        dataSource.setUsername(dataSourceProperties.getUsername());
        dataSource.setPassword(dataSourceProperties.getPassword());
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
                Sets.newHashSet(
                        "premoderation_queue.table.sql",
                        "source_offset.table.sql",
                        "authorization_meta.table.sql"));
    }

    @Bean
    ConfigurationLoader configurationLoader() {
        return Configurations.newXmlConfigurationLoader();
    }

    @Bean
    ExecutorService outgoingRequestExecutor(AggregatorProperties aggregatorProperties) {
        return Executors.newFixedThreadPool(aggregatorProperties.getOutputRequestThreadPoolSize(),
                new ThreadFactory() {
                    private final AtomicInteger threadNumber = new AtomicInteger(1);

                    @Override
                    public Thread newThread(Runnable runnable) {
                        return new Thread(runnable, "outgoing-request-" + threadNumber.getAndIncrement());
                    }
                });
    }

    @Bean
    HttpClient httpClient() {
        // todo client settings
        return HttpClientBuilder.create().build();
    }

    @Bean
    OutgoingRequestService outgoingRequestService(ExecutorService outgoingRequestExecutor, HttpClient httpClient) {
        return new OutgoingRequestService(httpClient, outgoingRequestExecutor);
    }

    @Bean
    ObjectMapper applicationObjectMapper() {
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);
        objectMapper.registerModule(new Jdk8Module());
        return objectMapper;
    }
}
