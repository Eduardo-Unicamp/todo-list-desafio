package com.saldanha.todo_list.exception;

public class PassingInvalidUserIdException extends RuntimeException {
  public PassingInvalidUserIdException(String message) {
    super(message);
  }
}
