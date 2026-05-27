package com.saldanha.todo_list.exception;

import com.saldanha.todo_list.enums.Priority;
import org.springframework.dao.DataIntegrityViolationException;
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
        return ResponseEntity.status(404).body(e.getMessage());
    }

    @ExceptionHandler(EmptyRequiredFieldsException.class)
    public ResponseEntity<String> EmptyRequiredFieldsExceptionHandler(EmptyRequiredFieldsException e){
        return ResponseEntity.status(400).body(e.getMessage());
    }

    @ExceptionHandler(PassingInvalidUserIdException.class)
    public ResponseEntity<String> PassingInvalidUserIdExceptionHandler(PassingInvalidUserIdException e){
        return ResponseEntity.status(400).body(e.getMessage());
    }

    @ExceptionHandler(DataIntegrityViolationException.class)
    public ResponseEntity<String> DataIntegrityViolationExceptionHandler(DataIntegrityViolationException e){
        if(e.getMessage() !=null && e.getMessage().contains("users_email_key")){
            //se for por causa do email repetido
            return ResponseEntity.status(409).body("O email informado já está em uso!");
        }
        //se for alguma outra coisa
        return ResponseEntity.status(400).body("Erro de violação de integridade de dados!");



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
