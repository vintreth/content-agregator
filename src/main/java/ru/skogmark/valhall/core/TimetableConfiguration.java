package ru.skogmark.valhall.core;

import javax.xml.bind.annotation.XmlElement;
import java.util.List;

public class TimetableConfiguration {
    @XmlElement(name = "time")
    private List<String> times;

    public List<String> getTimes() {
        return times;
    }
}
