package com.borjius.grpc.hello;

import com.borjius.grpc.auto.HelloRequest;
import com.borjius.grpc.auto.HelloResponse;
import com.borjius.grpc.auto.HelloServiceGrpc;
import net.devh.boot.grpc.client.inject.GrpcClient;
import net.devh.boot.grpc.client.security.CallCredentialsHelper;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;

import java.util.concurrent.ExecutionException;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(properties = {
        "grpc.server.inProcessName=test", // Enable inProcess server
        "grpc.server.port=-1", // Disable external server
        "grpc.client.inProcess.address=in-process:test" // Configure the client to connect to the inProcess server
})
@DirtiesContext
class HelloServiceImplTest {

    @GrpcClient("inProcess")
    private HelloServiceGrpc.HelloServiceFutureStub clientStub;

    @Test
    void testServer() throws ExecutionException, InterruptedException {

        final HelloRequest request = HelloRequest.newBuilder()
                .setFirstName("John")
                .setLastName("Doe")
                .build();
        this.clientStub = clientStub.withCallCredentials(
                CallCredentialsHelper.basicAuth("userMine", "password22"));
        final HelloResponse response = clientStub.hello(request).get();
        assertNotNull(response);
        assertEquals("Hello, Johnny Doe", response.getGreeting());

    }

    @Test
    void testServerAuthenticationException() {
        final HelloRequest request = HelloRequest.newBuilder()
                .setFirstName("John")
                .setLastName("Doe")
                .build();
        this.clientStub = clientStub.withCallCredentials(
                CallCredentialsHelper.basicAuth("userMineWrong", "password22"));
        assertThrows(ExecutionException.class, () -> clientStub.hello(request).get());
    }
}
