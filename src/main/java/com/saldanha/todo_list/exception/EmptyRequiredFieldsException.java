package com.saldanha.todo_list.exception;

public class EmptyRequiredFieldsException extends RuntimeException {
  public EmptyRequiredFieldsException(String message) {
    super(message);
  }
  public EmptyRequiredFieldsException(){
      super("Some of the required fields are empty");
  }


}
