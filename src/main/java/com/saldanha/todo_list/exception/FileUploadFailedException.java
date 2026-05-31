package com.saldanha.todo_list.exception;

public class FIleUplodFailedException extends RuntimeException {
  public FIleUplodFailedException(String message) {
    super(message);
  }
    public FIleUplodFailedException() {
        super("Failed to upload files to the bucket");
    }
}
