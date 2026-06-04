package com.saldanha.todo_list.exception;

public class TaskNotFoundException extends RuntimeException {
    public TaskNotFoundException(String message) {
        super(message);
    }
    //construtor vazio com mensagem padrão
    public TaskNotFoundException(){
        super("O id procurado não corresponde a nenhuma tarefa!");
    }

}
