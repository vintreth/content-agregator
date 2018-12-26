package ru.skogmark.valhall;

import it.ernytech.tdbot.EasyClient;
import it.ernytech.tdlib.Response;
import it.ernytech.tdlib.TdApi;
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
public class MainControllerTest {
    @Test
    public void testPing() throws Exception {
        HttpRequestHeader header = new HttpRequestHeader();
        header.setHttpMethod(HttpMethod.GET);
        header.setUrl("http://localhost:8185/ping");
        String response = new SerializerAwareHttpRequest(new JacksonObjectMapperSerializerAdapter())
                .makeRequest(header, null);
        assertEquals("ok", response);
    }

    @Test
    public void testConnection() throws Exception {
        TdApi.TdlibParameters parameters = new TdApi.TdlibParameters();
        parameters.databaseDirectory = "tdlib";
        parameters.useMessageDatabase = true;
        parameters.useSecretChats = true;
        parameters.apiId = 94575;
        parameters.apiHash = "a3406de8d171bb422bb6ddf3bbd800e2";
        parameters.systemLanguageCode = "en";
        parameters.deviceModel = "Desktop";
        parameters.systemVersion = "Unknown";
        parameters.applicationVersion = "1.0";
        parameters.enableStorageOptimizer = true;
        EasyClient client = new EasyClient(easyClient -> easyClient.send(new TdApi.SetTdlibParameters(parameters)));

        // test Client.execute
        Response response = client.execute(new TdApi.GetTextEntities("@telegram /test_command https://telegram.org telegram.me @gif @test"));
        System.out.println(response);
    }
}