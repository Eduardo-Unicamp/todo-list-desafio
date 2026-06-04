package com.saldanha.todo_list.exception;

import java.io.IOException;

public class FileUploadFailedException extends IOException {
  public FileUploadFailedException(String message) {
    super(message);
  }
    public FileUploadFailedException() {
        super("Failed to upload files to the bucket");
    }
}
