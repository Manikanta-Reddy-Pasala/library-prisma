package com.prisma.library.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import javax.servlet.http.HttpServletRequest;
import java.time.LocalDateTime;

@ControllerAdvice
public class GlobalControllerExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(NotFoundException.class)
    public ResponseEntity<Object> handleNotFoundException(
            NotFoundException ex, HttpServletRequest request) {

        ExceptionResponse exceptionResponse = new ExceptionResponse(ex.getMessage(), request.getRequestURI(), LocalDateTime.now());

        return new ResponseEntity<>(exceptionResponse, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(NoResultsFoundException.class)
    public ResponseEntity<Object> handleNoResultsFoundException(
            NoResultsFoundException ex, HttpServletRequest request) {

        ExceptionResponse exceptionResponse = new ExceptionResponse(ex.getMessage(), request.getRequestURI(), LocalDateTime.now());

        return new ResponseEntity<>(exceptionResponse, HttpStatus.NOT_FOUND);
    }
}
