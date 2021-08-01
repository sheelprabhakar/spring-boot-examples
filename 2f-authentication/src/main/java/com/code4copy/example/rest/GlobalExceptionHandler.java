package com.code4copy.example.rest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;

import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import javax.persistence.EntityExistsException;
import javax.persistence.EntityNotFoundException;
import javax.validation.ConstraintViolationException;


@ControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {
    Logger logger = LoggerFactory.getLogger(GlobalExceptionHandler.class);
    @Value("${code4copy.trace:false}")
    private boolean printStackTrace;

    @ExceptionHandler(EntityNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ResponseEntity<Object> handleItemNotFoundException(
            EntityNotFoundException itemNotFoundException,
            WebRequest request
    ){

       if(printStackTrace){
           itemNotFoundException.printStackTrace();
       }
        return ResponseEntity
                .status(HttpStatus.NOT_FOUND)
                .body(itemNotFoundException.getMessage());
    }

    @ExceptionHandler(ConstraintViolationException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ResponseEntity<Object> invalidParameter(
            ConstraintViolationException constraintViolationException,
            WebRequest request
    ){
        if(printStackTrace){
            constraintViolationException.printStackTrace();
        }
        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(constraintViolationException.getMessage());
    }

    @ExceptionHandler(EntityExistsException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ResponseEntity<Object> entityExistException(
            EntityExistsException entityExistsException,
            WebRequest request
    ){
        if(printStackTrace){
            entityExistsException.printStackTrace();
        }
        return ResponseEntity
                .status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(entityExistsException.getMessage());
    }

}
