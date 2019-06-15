package ru.skogmark.aggregator.prop;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import ru.skogmark.aggregator.ApplicationContextAwareTest;

import static org.junit.Assert.assertEquals;

public class AggregatorPropertiesTest extends ApplicationContextAwareTest {
    @Autowired
    private AggregatorProperties properties;

    @Test
    public void should_return_values_from_test_config() {
        assertEquals(20, properties.getOutputRequestThreadPoolSize());
        assertEquals(500, properties.getHttpClient().getConnectionTimeout());
        assertEquals(10000, properties.getHttpClient().getSocketTimeout());
    }
}