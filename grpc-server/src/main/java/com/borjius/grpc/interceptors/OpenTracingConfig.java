package com.borjius.grpc.interceptors;

import io.jaegertracing.internal.JaegerTracer;
import io.opentracing.Tracer;
import io.opentracing.contrib.grpc.TracingServerInterceptor;
import net.devh.boot.grpc.server.interceptor.GrpcGlobalServerInterceptor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;

@Configuration
public class OpenTracingConfig {

    @Bean
    public Tracer createTracer() {
        return new JaegerTracer.Builder("grpc-server").build();
    }

    @GrpcGlobalServerInterceptor
    @Order(1)
    public TracingServerInterceptor tracingInterceptor(final Tracer tracer) {
        return TracingServerInterceptor
                .newBuilder()
                .withTracer(tracer)
                .withVerbosity()
                .withStreaming()
                .withTracedAttributes(TracingServerInterceptor.ServerRequestAttribute.HEADERS,
                        TracingServerInterceptor.ServerRequestAttribute.METHOD_TYPE)
                .build();
    }
}
