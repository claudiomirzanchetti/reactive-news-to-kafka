package com.example.reactivenews2kafka.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

public class InvalidArgumentException extends ResponseStatusException {

    public InvalidArgumentException(String reason, Throwable cause) {
        super(HttpStatus.BAD_REQUEST);
    }

    public InvalidArgumentException(String reason) {
        super(HttpStatus.BAD_REQUEST, reason);
    }
}
