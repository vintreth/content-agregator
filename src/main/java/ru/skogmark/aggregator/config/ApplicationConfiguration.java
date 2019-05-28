package ru.skogmark.aggregator.config;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class ApplicationConfiguration {
    @XmlAttribute
    private int outputRequestThreadPoolSize;

    public int getOutputRequestThreadPoolSize() {
        return outputRequestThreadPoolSize;
    }
}
