package com.borjius.grpc.interceptors;

import io.grpc.Metadata;
import io.grpc.ServerCall;
import io.grpc.ServerCallHandler;
import io.grpc.ServerInterceptor;
import net.devh.boot.grpc.server.interceptor.GrpcGlobalServerInterceptor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.annotation.Order;

@GrpcGlobalServerInterceptor
@Order(2)
public final class MyGlobalGrpcInterceptor implements ServerInterceptor {

    private static final Logger LOGGER = LoggerFactory.getLogger(MyGlobalGrpcInterceptor.class);
    @Override
    public <ReqT, RespT> ServerCall.Listener<ReqT> interceptCall(final ServerCall<ReqT, RespT> serverCall,
                                                                 final Metadata metadata,
                                                                 final ServerCallHandler<ReqT, RespT>
                                                                             serverCallHandler) {
        LOGGER.info("Intercepting globally");
        return serverCallHandler.startCall(serverCall, metadata);
    }
}
