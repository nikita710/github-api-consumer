package com.demo.apiconsumer.model;

public record ErrorResponse(
        int status,
        String message
) {
}
