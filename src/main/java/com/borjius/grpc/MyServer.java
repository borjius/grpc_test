package com.borjius.grpc;

import com.borjius.grpc.hello.HelloServiceImpl;
import io.grpc.Server;
import io.grpc.ServerBuilder;

import java.io.IOException;

public class MyServer {

    public void launchServer() {
        final Server server = ServerBuilder
                .forPort(8080)
                .addService(new HelloServiceImpl()).build();

        try {
            server.start();
            server.awaitTermination();
        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

    }
}
