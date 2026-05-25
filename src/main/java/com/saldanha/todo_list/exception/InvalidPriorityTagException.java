package com.saldanha.todo_list.exception;

import com.saldanha.todo_list.enums.Priority;

import java.util.Arrays;

public class InvalidPriorityTagException extends RuntimeException {
    public InvalidPriorityTagException(String message) {
        super(message);
    }
    //construtor vazio com mensagem padrão
    public InvalidPriorityTagException(){
        super(String.format("The values for \"priority\" field can only be %s", Arrays.toString(Priority.values())));
    }
}
