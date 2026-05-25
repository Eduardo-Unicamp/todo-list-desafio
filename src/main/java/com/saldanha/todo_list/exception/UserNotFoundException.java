package com.saldanha.todo_list.exception;


public class UserNotFoundException extends RuntimeException {
    public UserNotFoundException(String message) {
        super(message);
    }
    //construtor vazio com mensagem padrão
    public UserNotFoundException(){
        super("O id procurado não corresponde a nenhum usuário!");
    }

}
