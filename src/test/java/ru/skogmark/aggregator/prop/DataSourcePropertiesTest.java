package ru.skogmark.aggregator.prop;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import ru.skogmark.aggregator.ApplicationContextAwareTest;

import static org.junit.Assert.assertEquals;

public class DataSourcePropertiesTest extends ApplicationContextAwareTest {
    @Autowired
    private DataSourceProperties properties;

    @Test
    public void should_return_values_from_test_config() {
        assertEquals("jdbc:postgresql://localhost:5432/aggregator", properties.getUrl());
        assertEquals("aggregator", properties.getUsername());
        assertEquals("aggregator", properties.getPassword());
    }
}