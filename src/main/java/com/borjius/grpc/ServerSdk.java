package com.borjius.grpc;

import com.sun.net.httpserver.HttpServer;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.util.concurrent.Executors;

public class ServerSdk {

    public void launchServer() throws IOException {
        final HttpServer server = HttpServer.create(new InetSocketAddress(8080), 0);

        server.createContext("/my/path", exchange -> {

            if ("POST".equals(exchange.getRequestMethod())) {
                final InputStream requestBody = exchange.getRequestBody();
                final String body = new String(requestBody.readAllBytes());
                final String response = "Hey hey";

                exchange.sendResponseHeaders(200, response.length());

                try (final OutputStream outputStream = exchange.getResponseBody()) {
                    outputStream.write(response.getBytes());
                    outputStream.flush();
                } catch (final IOException e) {
                    e.printStackTrace();
                    exchange.sendResponseHeaders(500, -1);
                }
            } else {
                exchange.sendResponseHeaders(405, -1);
            }


        });

        server.setExecutor(Executors.newFixedThreadPool(4));
        server.start();
    }
}
