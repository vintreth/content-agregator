package ru.skogmark.valhall.core.config;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.List;

@XmlRootElement
public class SubjectsConfiguration {
    @XmlElement(name = "subject")
    private List<Subject> subjects;

    public List<Subject> getSubjects() {
        return subjects;
    }

    public static class Subject {
        @XmlAttribute
        private int id;

        @XmlAttribute
        private String name;

        @XmlElement(name = "source")
        private List<Source> sources;

        public int getId() {
            return id;
        }

        public String getName() {
            return name;
        }

        public List<Source> getSources() {
            return sources;
        }
    }

    public static class Source {
        @XmlAttribute
        private int id;

        @XmlAttribute
        private int parsingLimit;

        @XmlElement
        private Timetable timetable;

        public Timetable getTimetable() {
            return timetable;
        }
    }

    public static class Timetable {
        @XmlElement(name = "time")
        private List<String> times;

        public List<String> getTimes() {
            return times;
        }
    }
}
