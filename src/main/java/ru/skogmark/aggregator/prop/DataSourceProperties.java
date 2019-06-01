package ru.skogmark.aggregator.prop;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import ru.skogmark.framework.Application;

@Configuration
@ConfigurationProperties(prefix = "data-source")
@PropertySource(Application.CONFIG_LOCATION_PROPERTY + "/data-source.properties")
public class DataSourceProperties {
    private String url;
    private String username;
    private String password;

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
