package io.avinash.ats.fileservice.advice;

import io.avinash.ats.fileservice.exception.IdNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(IdNotFoundException.class)
    public ResponseEntity<String> handleIdNotFoundException(IdNotFoundException ex){
        return new ResponseEntity<>("Id does not exists", HttpStatus.NOT_FOUND);

    }
}
