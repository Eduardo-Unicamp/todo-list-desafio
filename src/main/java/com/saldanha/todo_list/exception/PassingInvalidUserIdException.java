package com.saldanha.todo_list.exception;

public class PassingInvalidUserIdException extends RuntimeException {
  public PassingInvalidUserIdException(String message) {
    super(message);
  }
  public PassingInvalidUserIdException(){
      super("Creating an user with arbitrary userId is not allowed.\n You must either not include userdId field" +
              "(creates new user) or include the userId of a existent user(updates his fields)");
  }
}
