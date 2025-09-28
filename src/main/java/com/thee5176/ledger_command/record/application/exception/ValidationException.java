package com.thee5176.ledger_command.record.application.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value=HttpStatus.BAD_REQUEST, reason="Validation failed")
public class ValidationException extends RuntimeException {
    public ValidationException(String message) {
        super(message);
    }
}
