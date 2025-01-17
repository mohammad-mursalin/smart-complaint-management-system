package com.mursalin.SCMS.exceptionHandler;

public class EmailSendingException extends RuntimeException {
    public EmailSendingException(String message) {
        super(message);
    }
}
