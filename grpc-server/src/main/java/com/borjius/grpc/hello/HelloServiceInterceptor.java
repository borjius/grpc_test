package com.borjius.grpc.hello;

import com.borjius.grpc.auto.HelloRequest;
import io.grpc.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.annotation.Order;

@Order(3)
public class HelloServiceInterceptor implements ServerInterceptor {

    private static final Logger LOGGER = LoggerFactory.getLogger(HelloServiceInterceptor.class);
    @Override
    public <ReqT, RespT> ServerCall.Listener<ReqT> interceptCall(ServerCall<ReqT, RespT> call, Metadata headers,
                                                                 ServerCallHandler<ReqT, RespT> next) {
        LOGGER.info("In Hello interceptor");
        return new ForwardingServerCallListener.SimpleForwardingServerCallListener<ReqT>(next.startCall(call, headers)) {
            @Override
            public void onMessage(ReqT request) {
                final HelloRequest helloRequest = (HelloRequest) request;
                if (helloRequest.getFirstName().equals("John")) {
                    request = (ReqT) HelloRequest.newBuilder()
                            .setFirstName("Johnny")
                            .setLastName("Doe")
                            .build();
                }
                super.onMessage(request);
            }
        };
    }
}
