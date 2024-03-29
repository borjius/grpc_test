package com.borjius.hello.grpc.client;

import com.borjius.grpc.auto.HelloRequest;
import com.borjius.grpc.auto.HelloServiceGrpc;
import io.grpc.CallCredentials;
import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import net.devh.boot.grpc.client.security.CallCredentialsHelper;

public class HelloClient {

    private static final String DESTINATION_HOST = "localhost";
    private static final int DESTINATION_PORT = 8080;

    private HelloServiceGrpc.HelloServiceBlockingStub regularStub;

    // Im not gonna check credentials content..
    public HelloClient(final BasicCredentials credentials) {
        final ManagedChannel managedChannel = ManagedChannelBuilder
                .forAddress(DESTINATION_HOST, DESTINATION_PORT)
                .usePlaintext()
                .build();
        final CallCredentials callCredentials = CallCredentialsHelper.basicAuth(credentials.getUsername(),
                credentials.getPassword());
        this.regularStub = HelloServiceGrpc.newBlockingStub(managedChannel).withCallCredentials(callCredentials);
    }

    public HelloClient() {
        final ManagedChannel managedChannel = ManagedChannelBuilder
                .forAddress(DESTINATION_HOST, DESTINATION_PORT)
                .usePlaintext()
                .build();
        this.regularStub = HelloServiceGrpc.newBlockingStub(managedChannel);
    }

    public String hello(final String firstName, final String lastName) {
        final HelloRequest helloRequest = createRequest(firstName, lastName);
        return regularStub.hello(helloRequest).getGreeting();
    }

    public int numbers(final String firstName, final String lastName) {
        final HelloRequest helloRequest = createRequest(firstName, lastName);
        return regularStub.numbers(helloRequest).getNumberOfElements();
    }

    private HelloRequest createRequest(final String firstName, final String lastName) {
        return HelloRequest.newBuilder()
                .setFirstName(firstName)
                .setLastName(lastName)
                .build();
    }
}
