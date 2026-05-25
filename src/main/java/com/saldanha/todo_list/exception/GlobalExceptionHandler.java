package com.saldanha.todo_list.exception;

import com.saldanha.todo_list.enums.Priority;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;
import tools.jackson.databind.exc.InvalidFormatException;

import java.util.Arrays;

@ControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {
    @ExceptionHandler(UserNotFoundException.class)
    public ResponseEntity<String> UserNotFoundExceptionHandler(UserNotFoundException e){
        return ResponseEntity.badRequest().body(e.getMessage());
    }

    @ExceptionHandler(EmptyRequiredFieldsException.class)
    public ResponseEntity<String> EmptyRequiredFieldsExceptionHandler(EmptyRequiredFieldsException e){
        return ResponseEntity.status(400).body(e.getMessage());
    }

    @Override
    protected ResponseEntity<Object> handleHttpMessageNotReadable(
            HttpMessageNotReadableException ex, HttpHeaders headers, HttpStatusCode status, WebRequest request
    ){
        if(ex.getCause() instanceof InvalidFormatException invalidFormatException){
            if(invalidFormatException.getTargetType() != null && invalidFormatException.getTargetType().isEnum()) {
                //InvalidPriorityTag
                return ResponseEntity.status(400).body(String.format("ERROR: The values for \"priority\" field can only be %s", Arrays.toString(Priority.values())));
            }
            }
        return ResponseEntity.status(400).body("Formato do JSON enviado inválido");
        }

}
