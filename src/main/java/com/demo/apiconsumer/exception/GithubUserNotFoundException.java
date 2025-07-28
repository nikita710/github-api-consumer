package com.demo.apiconsumer.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class GithubUserNotFoundException extends RuntimeException {
    public GithubUserNotFoundException(String message) {
        super(message);
    }
}
