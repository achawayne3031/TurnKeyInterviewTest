package com.TurnKeyInterviewTest.config;

import com.TurnKeyInterviewTest.exceptions.CustomException;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.http.converter.HttpMessageNotWritableException;
import org.springframework.orm.jpa.JpaSystemException;
import org.springframework.validation.FieldError;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.NoHandlerFoundException;

import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.resource.NoResourceFoundException;

import java.sql.SQLIntegrityConstraintViolationException;
import java.util.List;
import java.util.stream.Collectors;

@Order(Ordered.HIGHEST_PRECEDENCE)
@ControllerAdvice
@RestControllerAdvice
public class RestExecptionHandler {


    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ApiResponse> handleValidationErrors(MethodArgumentNotValidException ex) {
        List<String> errors = ex.getBindingResult().getFieldErrors()
                .stream().map(FieldError::getDefaultMessage).collect(Collectors.toList());
        return new ResponseEntity<>(new ApiResponse<Object>("validation errors", false, errors),
                HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity handleException(HttpMessageNotReadableException exception, HttpServletRequest request) {
        return new ResponseEntity<>(new ApiResponse<Object>(exception.getMessage(), false), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(HttpRequestMethodNotSupportedException.class)
    public ResponseEntity handleHttpRequestMethodNotSuppoertedException(
            HttpRequestMethodNotSupportedException exception, HttpServletRequest request) {
        return new ResponseEntity<>(new ApiResponse<Object>(exception.getMessage(), false), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(CustomException.class)
    public ResponseEntity handleCustomExceptionException(CustomException exception, HttpServletRequest request) {
        return new ResponseEntity<>(new ApiResponse<Object>(exception.getMessage(), false), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(NullPointerException.class)
    public ResponseEntity handleNullPointerExceptionException(NullPointerException exception,
            HttpServletRequest request) {
        return new ResponseEntity<>(new ApiResponse<Object>(exception.getMessage(), false), HttpStatus.FORBIDDEN);
    }

    @ExceptionHandler(SQLIntegrityConstraintViolationException.class)
    public ResponseEntity handleSQLIntegrityConstraintViolationExceptionException(
            SQLIntegrityConstraintViolationException exception, HttpServletRequest request) {
        return new ResponseEntity<>(new ApiResponse<Object>(exception.getMessage(), false), HttpStatus.FORBIDDEN);
    }

    @ExceptionHandler(HttpMessageNotWritableException.class)
    public ResponseEntity handleHttpMessageNotWritableException(HttpMessageNotWritableException exception,
            HttpServletRequest request) {
        return new ResponseEntity<>(new ApiResponse<Object>(exception.getMessage(), false), HttpStatus.FORBIDDEN);
    }

    @ExceptionHandler(JpaSystemException.class)
    public ResponseEntity handleJpaSystemException(JpaSystemException exception, HttpServletRequest request) {
        return new ResponseEntity<>(new ApiResponse<Object>(exception.getMessage(), false), HttpStatus.FORBIDDEN);
    }

    @ExceptionHandler(NoResourceFoundException.class)
    public ResponseEntity handleNoResourceFoundException(NoResourceFoundException exception,
            HttpServletRequest request) {
        return new ResponseEntity<>(new ApiResponse<Object>(exception.getMessage(), false), HttpStatus.NOT_FOUND);
    }


    @ExceptionHandler({ NoHandlerFoundException.class })
    public ResponseEntity<ApiResponse> handleNoHandlerFoundException(
            NoHandlerFoundException ex, HttpServletRequest httpServletRequest) {
        return new ResponseEntity<>(new ApiResponse<>((ex.getMessage()), false), HttpStatus.NOT_FOUND);
    }

}
