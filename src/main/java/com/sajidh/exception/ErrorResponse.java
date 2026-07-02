package com.sajidh.exception;

import lombok.Value;

import java.time.LocalDateTime;
import java.util.List;

@Value
public class ErrorResponse {

    LocalDateTime timestamp;

    int status;

    String error;

    String message;

    List<String> errors;

    String path;

}