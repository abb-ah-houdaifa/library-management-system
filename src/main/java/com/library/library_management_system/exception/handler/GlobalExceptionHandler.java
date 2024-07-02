package com.library.library_management_system.exception.handler;

import com.library.library_management_system.exception.NotFoundException;
import com.library.library_management_system.exception.NullAttributeException;
import com.library.library_management_system.exception.OperationNotAllowedException;
import org.hibernate.exception.ConstraintViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(value = NotFoundException.class)
    public ResponseEntity<String> notFoundExceptionHandler(
            NotFoundException exception
    ){
        return new ResponseEntity<>(exception.getMessage(),HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(value = {
            NullPointerException.class,
            NullAttributeException.class,
            ConstraintViolationException.class
    })
    public ResponseEntity<String> invalidAttributeExceptionsHandler(
            RuntimeException exception
    ){
        return new ResponseEntity<>(exception.getMessage(),HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(value = OperationNotAllowedException.class)
    public ResponseEntity<String> operationNotAllowedExceptionHandler(
            OperationNotAllowedException exception
    ){
        return new ResponseEntity<>(exception.getMessage(), HttpStatus.NOT_ACCEPTABLE);
    }
}
