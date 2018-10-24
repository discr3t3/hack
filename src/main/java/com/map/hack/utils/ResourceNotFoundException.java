package com.map.hack.utils;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class ResourceNotFoundException extends RuntimeException {
    public static String message = "Could not access Resource: %s.";
    public static String message_exception = "Could not access Resource: %s with exception: %s";

    public static String formatMessage(String resource) {
        return String.format(message, resource);
    }

    public static String formatMessage(String resource, Exception exception) {
        return String.format(message_exception, resource, exception.toString());
    }

    public ResourceNotFoundException(String resource) {
        super(formatMessage(resource));
    }

    public ResourceNotFoundException(String resource, Exception exception) {
        super(formatMessage(resource, exception));
    }
}
