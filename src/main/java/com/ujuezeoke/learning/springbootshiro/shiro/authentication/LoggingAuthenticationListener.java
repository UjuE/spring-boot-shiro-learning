package com.ujuezeoke.learning.springbootshiro.shiro.authentication;

import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationListener;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.subject.PrincipalCollection;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component
public class LoggingAuthenticationListener implements AuthenticationListener {
    private static final Logger LOGGER = LoggerFactory.getLogger(LoggingAuthenticationListener.class);

    @Override
    public void onSuccess(AuthenticationToken token, AuthenticationInfo info) {

        LOGGER.info("Successfully Authenticated {} using {} token type", token.getPrincipal(),
                token.getClass().getSimpleName());
    }

    @Override
    public void onFailure(AuthenticationToken token, AuthenticationException ae) {
        LOGGER.info("Failed to Authenticate {} using {} token type", token.getPrincipal(),
                token.getClass().getSimpleName());
    }

    @Override
    public void onLogout(PrincipalCollection principals) {
        LOGGER.info("Logging out {}", principals.asList());
    }
}
