package com.mursalin.SCMS.exceptionHandler;

public class InvalidComplaintStateException extends RuntimeException {
    public InvalidComplaintStateException(String message) {
        super(message);
    }
}
