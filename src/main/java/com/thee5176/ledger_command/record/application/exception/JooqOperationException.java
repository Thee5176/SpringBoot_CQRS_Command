package com.thee5176.ledger_command.record.application.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.INTERNAL_SERVER_ERROR, reason = "JOOQ operation failed")
public class JooqOperationException extends RuntimeException {
    public JooqOperationException(String message) {
        super(message);
    }

    public JooqOperationException(String message, Throwable cause) {
        super(message, cause);
    }
}
