package com.borjius.grpc.hello;

import com.borjius.grpc.auto.HelloRequest;
import io.grpc.Metadata;
import io.grpc.ServerCall;
import io.grpc.ServerCallHandler;
import io.grpc.ServerInterceptor;
import io.grpc.ForwardingServerCallListener;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.annotation.Order;

@Order(HelloServiceInterceptor.PRIORITY)
public class HelloServiceInterceptor implements ServerInterceptor {

    public static final int PRIORITY = 3;
    private static final Logger LOGGER = LoggerFactory.getLogger(HelloServiceInterceptor.class);
    @Override
    public <ReqT, RespT> ServerCall.Listener<ReqT> interceptCall(final ServerCall<ReqT, RespT> call,
                                                                 final Metadata headers,
                                                                 final ServerCallHandler<ReqT, RespT> next) {
        LOGGER.info("In Hello interceptor");
        return new ForwardingServerCallListener
                .SimpleForwardingServerCallListener<ReqT>(next.startCall(call, headers)) {
            @Override
            public void onMessage(final ReqT request) {
                final HelloRequest helloRequest = (HelloRequest) request;
                if (helloRequest.getFirstName().equals("John")) {
                    final ReqT requestModified = (ReqT) HelloRequest.newBuilder()
                            .setFirstName("Johnny")
                            .setLastName("Doe")
                            .build();
                    super.onMessage(requestModified);
                } else {
                    super.onMessage(request);
                }

            }
        };
    }
}
