package com.borjius.hello.grpc.client;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

public class HelloAsyncClientTest {

    public static void main(String[] args) throws ExecutionException, InterruptedException {
        final BasicCredentials credentials = new BasicCredentials("userMine", "password22");
        final HelloAsyncClient asyncClient = new HelloAsyncClient(credentials);

        final CompletableFuture<String> helloResponse = asyncClient.hello("John", "Doe");
        final CompletableFuture<Integer> numbersResponse = asyncClient.numbers("John", "Doe");

        final CompletableFuture<String> resultCompletableFuture = helloResponse.thenCombine(numbersResponse, (greeting,
                                                                                                         elements) -> String.format("%s you have %d elements!", greeting, elements));
        System.out.println(resultCompletableFuture.get());
    }
}
