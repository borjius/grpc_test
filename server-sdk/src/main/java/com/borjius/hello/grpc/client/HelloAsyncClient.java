package com.borjius.hello.grpc.client;

import com.borjius.grpc.auto.HelloRequest;
import com.borjius.grpc.auto.HelloServiceGrpc;
import com.borjius.hello.grpc.util.FutureConverter;
import io.grpc.CallCredentials;
import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import net.devh.boot.grpc.client.security.CallCredentialsHelper;

import java.util.concurrent.CompletableFuture;

public class HelloAsyncClient {

    private static final String DESTINATION_HOST = "localhost";
    private static final int DESTINATION_PORT = 8080;

    private final HelloServiceGrpc.HelloServiceFutureStub futureStub;

    public HelloAsyncClient(final BasicCredentials credentials) {
        final ManagedChannel managedChannel = ManagedChannelBuilder
                .forAddress(DESTINATION_HOST, DESTINATION_PORT)
                .usePlaintext()
                .build();
        final CallCredentials callCredentials = CallCredentialsHelper.basicAuth(credentials.getUsername(),
                credentials.getPassword());
        this.futureStub = HelloServiceGrpc.newFutureStub(managedChannel)
                .withCallCredentials(callCredentials);
    }

    public HelloAsyncClient() {
        final ManagedChannel managedChannel = ManagedChannelBuilder
                .forAddress(DESTINATION_HOST, DESTINATION_PORT)
                .usePlaintext()
                .build();
        this.futureStub = HelloServiceGrpc.newFutureStub(managedChannel);
    }

    public CompletableFuture<String> hello(final String firstName, final String lastName) {
        final HelloRequest helloRequest = createRequest(firstName, lastName);
        return FutureConverter
                .convertToCompletable(futureStub.hello(helloRequest))
                        .thenApplyAsync(helloResponse -> helloResponse.getGreeting());
    }

    public CompletableFuture<Integer> numbers(final String firstName, final String lastName) {
        final HelloRequest helloRequest = createRequest(firstName, lastName);
        return FutureConverter
                .convertToCompletable(futureStub.numbers(helloRequest))
                .thenApplyAsync(helloResponse -> helloResponse.getNumberOfElements());
    }

    private HelloRequest createRequest(final String firstName, final String lastName) {
        return HelloRequest.newBuilder()
                .setFirstName(firstName)
                .setLastName(lastName)
                .build();
    }
}
