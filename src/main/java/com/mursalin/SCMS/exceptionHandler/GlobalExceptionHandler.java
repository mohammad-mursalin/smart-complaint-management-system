package com.mursalin.SCMS.exceptionHandler;

import com.mursalin.SCMS.dto.ErrorResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

    private static final Logger logger = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    @ExceptionHandler(UserAlreadyExistsException.class)
    public ResponseEntity<?> handleUserAlreadyExistsException(UserAlreadyExistsException ex) {
        logger.error("Error: {}", ex.getMessage());
        return new ResponseEntity<>(new ErrorResponse(ex.getMessage(), HttpStatus.CONFLICT.value()), HttpStatus.CONFLICT);
    }

    @ExceptionHandler(UserNotFoundException.class)
    public ResponseEntity<?> handleUserNotFoundException(UserNotFoundException ex) {
        logger.error("Error: {}", ex.getMessage());
        return new ResponseEntity<>(new ErrorResponse(ex.getMessage(), HttpStatus.NOT_FOUND.value()), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(UserNotVerifiedException.class)
    public ResponseEntity<?> handleUserNotVerifiedException(UserNotVerifiedException ex) {
        logger.error("Error: {}", ex.getMessage());
        return new ResponseEntity<>(new ErrorResponse(ex.getMessage(), HttpStatus.FORBIDDEN.value()), HttpStatus.FORBIDDEN);
    }

    @ExceptionHandler(UnauthorizedActionException.class)
    public ResponseEntity<?> handleUnauthorizedActionException(UnauthorizedActionException ex) {
        logger.error("Error: {}", ex.getMessage());
        return new ResponseEntity<>(new ErrorResponse(ex.getMessage(), HttpStatus.FORBIDDEN.value()), HttpStatus.FORBIDDEN);
    }

    @ExceptionHandler(InvalidComplaintStateException.class)
    public ResponseEntity<?> handleInvalidComplaintStateException(InvalidComplaintStateException ex) {
        logger.error("Error: {}", ex.getMessage());
        return new ResponseEntity<>(new ErrorResponse(ex.getMessage(), HttpStatus.BAD_REQUEST.value()), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(EmailSendingException.class)
    public ResponseEntity<?> handleEmailSendingException(EmailSendingException ex) {
        logger.error("Error: {}", ex.getMessage());
        return new ResponseEntity<>(new ErrorResponse(ex.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR.value()), HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(ComplaintNotFoundException.class)
    public ResponseEntity<?> handleComplaintNotFoundException(ComplaintNotFoundException ex) {
        logger.error("Error: {}", ex.getMessage());
        return new ResponseEntity<>(new ErrorResponse(ex.getMessage(), HttpStatus.NOT_FOUND.value()), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(AuthenticationFailedException.class)
    public ResponseEntity<?> handleAuthenticationFailedException(AuthenticationFailedException ex) {
        logger.error("Error: {}", ex.getMessage());
        return new ResponseEntity<>(new ErrorResponse(ex.getMessage(), HttpStatus.UNAUTHORIZED.value()), HttpStatus.UNAUTHORIZED);
    }

    @ExceptionHandler(JwtException.class)
    public ResponseEntity<?> handleJwtException(JwtException ex) {
        logger.error("Error: {}", ex.getMessage());
        return new ResponseEntity<>(new ErrorResponse(ex.getMessage(), HttpStatus.UNAUTHORIZED.value()), HttpStatus.UNAUTHORIZED);
    }

    @ExceptionHandler(CustomException.class)
    public ResponseEntity<?> handleCustomException(CustomException ex) {
        logger.error("Error: {}", ex.getMessage());
        return new ResponseEntity<>(new ErrorResponse(ex.getMessage(), ex.getHttpStatus().value()), ex.getHttpStatus());
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<?> handleGenericException(Exception ex) {
        logger.error("Unhandled error: {}", ex.getMessage());
        return new ResponseEntity<>(new ErrorResponse("An unexpected error occurred. Please try again later.", HttpStatus.INTERNAL_SERVER_ERROR.value()), HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
