package ru.skogmark.valhall.core.config;

import ru.skogmark.common.config.adapter.LocalTimeAdapter;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;
import java.time.LocalTime;
import java.util.List;

@XmlRootElement
public class SubjectsConfiguration {
    @XmlElement
    private Subjects subjects;

    public Subjects getSubjects() {
        return subjects;
    }

    public static class Subjects {
        @XmlElement(name = "subject")
        private List<Subject> items;

        public List<Subject> getItems() {
            return items;
        }
    }

    public static class Subject {
        @XmlAttribute
        private int id;

        @XmlAttribute
        private String name;

        @XmlElement
        private Sources sources;

        public int getId() {
            return id;
        }

        public String getName() {
            return name;
        }

        public Sources getSources() {
            return sources;
        }
    }

    public static class Sources {
        @XmlElement(name = "source")
        private List<Source> items;

        public List<Source> getItems() {
            return items;
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
        @XmlJavaTypeAdapter(LocalTimeAdapter.class)
        private List<LocalTime> times;

        public List<LocalTime> getTimes() {
            return times;
        }
    }
}
