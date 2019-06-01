package ru.skogmark.aggregator.prop;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import ru.skogmark.framework.Application;

import java.util.List;

@Configuration
@ConfigurationProperties(prefix = "vk-parser")
@PropertySource(Application.CONFIG_LOCATION_PROPERTY + "/vk-parser.properties")
public class VkParserProperties {
    private String apiUrl;
    private List<Long> userIds;
    private String accessToken;
    private String version;

    public String getApiUrl() {
        return apiUrl;
    }

    public void setApiUrl(String apiUrl) {
        this.apiUrl = apiUrl;
    }

    public List<Long> getUserIds() {
        return userIds;
    }

    public void setUserIds(List<Long> userIds) {
        this.userIds = userIds;
    }

    public String getAccessToken() {
        return accessToken;
    }

    public void setAccessToken(String accessToken) {
        this.accessToken = accessToken;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }
}
