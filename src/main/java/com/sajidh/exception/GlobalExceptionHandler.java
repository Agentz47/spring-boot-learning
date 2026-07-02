package com.sajidh.exception;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import jakarta.servlet.http.HttpServletRequest;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(
            MethodArgumentNotValidException.class
    )
    @ResponseStatus(
            HttpStatus.BAD_REQUEST
    )
    public ErrorResponse handleValidationErrors(
            MethodArgumentNotValidException ex,
            HttpServletRequest request
    ) {

        List<String> errors =
                new ArrayList<>();

        ex.getBindingResult()
                .getFieldErrors()
                .forEach(error ->
                        errors.add(
                                error.getDefaultMessage()
                        )
                );

        return new ErrorResponse(
                LocalDateTime.now(),
                HttpStatus.BAD_REQUEST.value(),
                HttpStatus.BAD_REQUEST.getReasonPhrase(),
                "Validation Failed",
                errors,
                request.getRequestURI()
        );
    }

    @ExceptionHandler(
            StudentNotFoundException.class
    )
    @ResponseStatus(
            HttpStatus.NOT_FOUND
    )
    public ErrorResponse handleStudentNotFound(
            StudentNotFoundException ex,
            HttpServletRequest request
    ) {

        return new ErrorResponse(
                LocalDateTime.now(),
                HttpStatus.BAD_REQUEST.value(),
                HttpStatus.BAD_REQUEST.getReasonPhrase(),
                ex.getMessage(),
                List.of(),
                request.getRequestURI()
        );
    }



    @ExceptionHandler(
            UsernameAlreadyExistsException.class
    )
    public ResponseEntity<ErrorResponse>
    handleUsernameAlreadyExists(
            UsernameAlreadyExistsException ex,
            HttpServletRequest request
    ) {

        ErrorResponse response =
                new ErrorResponse(
                        LocalDateTime.now(),
                        HttpStatus.CONFLICT.value(),
                        HttpStatus.CONFLICT.getReasonPhrase(),
                        ex.getMessage(),
                        List.of(),
                        request.getRequestURI()
                );

        return ResponseEntity
                .status(HttpStatus.CONFLICT)
                .body(response);
    }

    @ExceptionHandler(
            RefreshTokenException.class
    )
    public ResponseEntity<ErrorResponse>
    handleRefreshToken(
            RefreshTokenException ex,
            HttpServletRequest request
    ) {
        ErrorResponse response =
                new ErrorResponse(
                        LocalDateTime.now(),
                        HttpStatus.UNAUTHORIZED.value(),
                        HttpStatus.UNAUTHORIZED.getReasonPhrase(),
                        ex.getMessage(),
                        List.of(),
                        request.getRequestURI()
                );

        return ResponseEntity
                .status(HttpStatus.UNAUTHORIZED)
                .body(response);
    }
}