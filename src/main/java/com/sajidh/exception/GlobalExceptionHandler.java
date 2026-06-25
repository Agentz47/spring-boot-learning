package com.sajidh.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

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
            MethodArgumentNotValidException ex
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
                "Validation Failed",
                errors
        ) {
        };
    }

    @ExceptionHandler(
            StudentNotFoundException.class
    )
    @ResponseStatus(
            HttpStatus.NOT_FOUND
    )
    public ErrorResponse handleStudentNotFound(
            StudentNotFoundException ex
    ) {

        return new ErrorResponse(
                ex.getMessage(),
                List.of()
        );
    }

    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<String> handleRuntimeException(
            RuntimeException ex
    ) {

        return ResponseEntity.badRequest()
                .body(ex.getMessage());
    }

    @ExceptionHandler(
            UsernameAlreadyExistsException.class
    )
    public ResponseEntity<String>
    handleUsernameAlreadyExists(
            UsernameAlreadyExistsException ex
    ) {

        return ResponseEntity.badRequest()
                .body(ex.getMessage());
    }
}