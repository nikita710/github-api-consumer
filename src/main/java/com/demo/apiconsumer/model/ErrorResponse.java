package com.demo.apiconsumer.exception;

public record ErrorResponse(
        int status,
        String message
) {
}
