package com.borjius.grpc;

import com.borjius.grpc.auto.HelloRequest;
import com.borjius.grpc.auto.HelloResponse;
import com.borjius.grpc.auto.HelloServiceGrpc;
import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import org.junit.jupiter.api.Test;

import java.util.concurrent.CompletableFuture;

import static org.junit.jupiter.api.Assertions.assertEquals;

class AppTest {

    @Test
    void testServer() throws InterruptedException {

        CompletableFuture.runAsync(() ->
            new MyServer()
                        .launchServer()
        );

        Thread.sleep(3000);

        final ManagedChannel channel = ManagedChannelBuilder.forAddress("localhost", 8080)
                .usePlaintext()
                .build();

        final HelloServiceGrpc.HelloServiceBlockingStub stub
                = HelloServiceGrpc.newBlockingStub(channel);

        final HelloResponse helloResponse = stub.hello(HelloRequest.newBuilder()
                .setFirstName("John")
                .setLastName("Doe")
                .build());

        assertEquals("Hello, John Doe", helloResponse.getGreeting());

        channel.shutdown();
    }
}
