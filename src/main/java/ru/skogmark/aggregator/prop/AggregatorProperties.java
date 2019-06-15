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
    private HttpClient httpClient;

    public int getOutputRequestThreadPoolSize() {
        return outputRequestThreadPoolSize;
    }

    public void setOutputRequestThreadPoolSize(int outputRequestThreadPoolSize) {
        this.outputRequestThreadPoolSize = outputRequestThreadPoolSize;
    }

    public HttpClient getHttpClient() {
        return httpClient;
    }

    public AggregatorProperties setHttpClient(HttpClient httpClient) {
        this.httpClient = httpClient;
        return this;
    }

    public static class HttpClient {
        private int connectionTimeout;
        private int socketTimeout;

        public int getConnectionTimeout() {
            return connectionTimeout;
        }

        public HttpClient setConnectionTimeout(int connectionTimeout) {
            this.connectionTimeout = connectionTimeout;
            return this;
        }

        public int getSocketTimeout() {
            return socketTimeout;
        }

        public HttpClient setSocketTimeout(int socketTimeout) {
            this.socketTimeout = socketTimeout;
            return this;
        }
    }
}
