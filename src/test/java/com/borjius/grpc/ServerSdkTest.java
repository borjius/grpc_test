package com.borjius.grpc;

import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.http.HttpClient;

class ServerSdkTest {

    @Test
    void serverSdk() throws IOException, InterruptedException, URISyntaxException {
        final ServerSdk serverSdk = new ServerSdk();
        serverSdk.launchServer();

        Thread.sleep(4000);

        final HttpClient client = HttpClient.newHttpClient();
        final URI uri = new URI("http://localhost:8080/my/path");


    }
}
