package com.gpsolutions.hotels.error_handling.exception;

import org.springframework.http.HttpStatus;

import java.time.ZonedDateTime;

public record ExceptionResponse(String message,
                                HttpStatus httpStatus,
                                String description,
                                ZonedDateTime timestamp) {
}