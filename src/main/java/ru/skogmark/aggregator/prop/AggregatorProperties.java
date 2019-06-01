package ru.skogmark.aggregator.prop;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import ru.skogmark.framework.Application;

@Configuration
@ConfigurationProperties(prefix = "aggregator")
@PropertySource(Application.CONFIG_LOCATION_PROPERTY + "/aggregator.properties")
public class AggregatorProperties {
    private int outputRequestThreadPoolSize;

    public int getOutputRequestThreadPoolSize() {
        return outputRequestThreadPoolSize;
    }

    public void setOutputRequestThreadPoolSize(int outputRequestThreadPoolSize) {
        this.outputRequestThreadPoolSize = outputRequestThreadPoolSize;
    }
}
