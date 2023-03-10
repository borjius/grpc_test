package com.borjius.grpc.auth;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;

import java.util.ArrayList;


public class HelloAuthenticationProvider implements AuthenticationProvider {

    private static final Logger LOGGER = LoggerFactory.getLogger(HelloAuthenticationProvider.class);
    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        LOGGER.info("In HelloAuthenticationProvider.authenticate");
        final String password = (String) authentication.getCredentials();
        final String user = (String) authentication.getPrincipal();
        if ("userMine".equals(user) && "password22".equals(password)) {
            return new UsernamePasswordAuthenticationToken(
                    user, password, new ArrayList<>());
        } else {
            return null;
        }
    }

    @Override
    public boolean supports(Class<?> authentication) {
        LOGGER.info("In HelloAuthenticationProvider.supports");
        return authentication.equals(UsernamePasswordAuthenticationToken.class);
    }
}
