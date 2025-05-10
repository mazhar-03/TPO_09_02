package org.example.tpo_09.exceptionhandler;

import org.example.tpo_09.exceptions.InvalidDataException;
import org.example.tpo_09.exceptions.InvalidGenderException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class RestExceptionHandler {

    @ExceptionHandler(InvalidGenderException.class)
    public ResponseEntity<Void> handleInvalidGender(InvalidGenderException ex) {
        return ResponseEntity
                .badRequest()
                .header("Reason", ex.getMessage())
                .build();
    }

    @ExceptionHandler(InvalidDataException.class)
    public ResponseEntity<Void> handleInvalidData(InvalidDataException ex) {
        String msg = ex.getMessage();
        if (msg.toLowerCase().contains("age")) {
            return ResponseEntity
                    .status(499)
                    .header("Reason", msg)
                    .build();
        }
        return ResponseEntity
                .badRequest()
                .header("Reason", msg)
                .build();
    }
}

