package com.ujuezeoke.learning.springbootshiro.errorhandlers;

import org.apache.shiro.authz.AuthorizationException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.security.Principal;
import java.util.Optional;

@ControllerAdvice
public class ErrorHandler {
    private static final Logger LOGGER = LoggerFactory.getLogger(ErrorHandler.class);

    @ExceptionHandler(Exception.class)
    public String handleEntityNotFound(Exception ex, Model modelMap) {
        modelMap.addAttribute("status", 500);
        modelMap.addAttribute("message", ex.getLocalizedMessage());
        System.out.println("modelMap = " + modelMap);
        return "error";
    }

    @ExceptionHandler(AuthorizationException.class)
    public String handleUnAuthorized(AuthorizationException ex,
                                           Principal principal, Model modelMap) {
        LOGGER.error("{} performed unauthorized action. {}", Optional.ofNullable(principal).map(Principal::getName).orElse(null), ex.getMessage());
        modelMap.addAttribute("status", 401);
        modelMap.addAttribute("message", "Unauthorized");
        return "error";
    }
}
