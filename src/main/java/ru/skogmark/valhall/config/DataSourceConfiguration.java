package ru.skogmark.valhall.config;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class DataSourceConfiguration {
    @XmlAttribute
    private String url;

    @XmlAttribute
    private String username;

    @XmlAttribute
    private String password;

    public String getUrl() {
        return url;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }
}
