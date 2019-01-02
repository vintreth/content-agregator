package ru.skogmark.valhall;

import it.ernytech.tdbot.EasyClient;
import it.ernytech.tdlib.Response;
import it.ernytech.tdlib.TdApi;
import it.ernytech.tdlib.utils.LoadLibrary;
import org.junit.Test;

public class ClientTest {
//    @Test
    public void name() throws Throwable {
        System.out.println(LoadLibrary.getOs());
        LoadLibrary.load("libeay32");
        LoadLibrary.load("ssleay32");
        LoadLibrary.load("zlib1");
        Thread.sleep(1000);
        LoadLibrary.load("tdjni");

    }

//    @Test
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
