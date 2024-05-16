package com.ems.controller;

import com.ems.error.InvalidRequestBodyException;
import com.ems.error.ResourceNotFoundException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.stream.Collectors;

@ControllerAdvice
public class ExceptionAdvice {

    record ErrorDto(String message) {
    }

    @ExceptionHandler
    public ResponseEntity<Object> handleException(DataIntegrityViolationException e) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ErrorDto("The data you entered is invalid."));
    }

    @ExceptionHandler
    public ResponseEntity<Object> handleException(InvalidRequestBodyException e) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ErrorDto(e.getMessage()));
    }

    @ExceptionHandler
    public ResponseEntity<Object> handleException(MethodArgumentNotValidException e) {
        String errorMessage = e.getBindingResult().getAllErrors().stream()
                .map(ObjectError::getDefaultMessage)
                .collect(Collectors.joining(", "));
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ErrorDto(errorMessage));
    }

    @ExceptionHandler
    public ResponseEntity<Object> handleException(ResourceNotFoundException e) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ErrorDto(e.getMessage()));
    }

    @ExceptionHandler()
    public ResponseEntity<Object> handleException(BadCredentialsException e) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ErrorDto(e.getMessage()));
    }

    @ExceptionHandler
    public ResponseEntity<Object> handleException(AuthenticationException e) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ErrorDto(e.getMessage()));
    }

    @ExceptionHandler
    public ResponseEntity<Object> handleException(Exception e) {
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ErrorDto(e.getMessage()));
    }
}
