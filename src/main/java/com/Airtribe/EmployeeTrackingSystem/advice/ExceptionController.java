package com.Airtribe.EmployeeTrackingSystem.advice;

import com.Airtribe.EmployeeTrackingSystem.exception.DataAlreadyExistException;
import com.Airtribe.EmployeeTrackingSystem.exception.InvalidDataException;
import com.Airtribe.EmployeeTrackingSystem.exception.ResourceNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class ExceptionController {

    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<String> resourceNotFoundException(ResourceNotFoundException ex) {
        return new ResponseEntity<>(ex.getMessage(), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(DataAlreadyExistException.class)
    public ResponseEntity<String> dataAlreadyExistException(DataAlreadyExistException ex) {
        return new ResponseEntity<>(ex.getMessage(), HttpStatus.CONFLICT);
    }

    @ExceptionHandler(InvalidDataException.class)
    public ResponseEntity<String> invalidDataException(InvalidDataException ex) {
        return new ResponseEntity<>(ex.getMessage(), HttpStatus.NOT_ACCEPTABLE);
    }

}