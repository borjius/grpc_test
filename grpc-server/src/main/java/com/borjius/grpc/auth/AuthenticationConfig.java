package com.borjius.grpc.auth;

import com.borjius.grpc.auto.HelloServiceGrpc;
import net.devh.boot.grpc.server.security.authentication.BasicGrpcAuthenticationReader;
import net.devh.boot.grpc.server.security.authentication.CompositeGrpcAuthenticationReader;
import net.devh.boot.grpc.server.security.authentication.GrpcAuthenticationReader;
import net.devh.boot.grpc.server.security.check.AccessPredicate;
import net.devh.boot.grpc.server.security.check.GrpcSecurityMetadataSource;
import net.devh.boot.grpc.server.security.check.ManualGrpcSecurityMetadataSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.ProviderManager;

import java.util.ArrayList;
import java.util.List;

@Configuration
public class AuthenticationConfig {

    @Bean
    public GrpcAuthenticationReader authenticationReader() {
        final List<GrpcAuthenticationReader> readers = new ArrayList<>();
        readers.add(new BasicGrpcAuthenticationReader());
        return new CompositeGrpcAuthenticationReader(readers);
    }

    @Bean
    public AuthenticationManager authenticationManager() {
        final List<AuthenticationProvider> providers = new ArrayList<>();

        providers.add(new HelloAuthenticationProvider());
        return new ProviderManager(providers);
    }

    @Bean
    public GrpcSecurityMetadataSource grpcSecurityMetadataSource() {
        final ManualGrpcSecurityMetadataSource source = new ManualGrpcSecurityMetadataSource();
        source.set(HelloServiceGrpc.METHOD_HELLO, AccessPredicate.authenticated());
        source.set(HelloServiceGrpc.METHOD_NUMBERS, AccessPredicate.permitAll());
        source.setDefault(AccessPredicate.denyAll());
        return source;
    }
}
