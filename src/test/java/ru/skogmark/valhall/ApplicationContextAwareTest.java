package ru.skogmark.valhall;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import ru.skogmark.common.http.HttpMethod;
import ru.skogmark.common.http.HttpRequestHeader;
import ru.skogmark.common.http.JacksonObjectMapperSerializerAdapter;
import ru.skogmark.common.http.SerializerAwareHttpRequest;

import static org.junit.Assert.assertEquals;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
@RunWith(SpringJUnit4ClassRunner.class)
public class ApplicationContextAwareTest {
    @Test
    public void testPing() throws Exception {
        HttpRequestHeader header = new HttpRequestHeader();
        header.setHttpMethod(HttpMethod.GET);
        header.setUrl("http://localhost:8185/ping");
        String response = new SerializerAwareHttpRequest(new JacksonObjectMapperSerializerAdapter())
                .makeRequest(header, null);
        assertEquals("ok", response);
    }
}