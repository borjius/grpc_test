package com.borjius.grpc.hello;

import com.borjius.grpc.auto.HelloRequest;
import com.borjius.grpc.auto.HelloResponse;
import com.borjius.grpc.auto.HelloServiceGrpc;
import com.borjius.grpc.auto.NumberResponse;
import io.grpc.stub.StreamObserver;
import net.devh.boot.grpc.server.service.GrpcService;

import java.util.Random;

@GrpcService(interceptors = HelloServiceInterceptor.class)
public class HelloServiceImpl extends HelloServiceGrpc.HelloServiceImplBase {

    private static final Random RANDOM = new Random();

    @Override
    public void hello(final HelloRequest request, final StreamObserver<HelloResponse> responseObserver) {
        final String greeting = new StringBuilder()
                .append("Hello, ")
                .append(request.getFirstName())
                .append(" ")
                .append(request.getLastName())
                .toString();
        final HelloResponse response = HelloResponse.newBuilder()
                .setGreeting(greeting)
                .build();

        responseObserver.onNext(response);
        responseObserver.onCompleted();
    }

    @Override
    public void numbers(final HelloRequest request, final StreamObserver<NumberResponse> responseObserver) {
        final NumberResponse response = NumberResponse.newBuilder()
                .setNumberOfElements(RANDOM.nextInt(10))
                .build();

        responseObserver.onNext(response);
        responseObserver.onCompleted();
    }
}
