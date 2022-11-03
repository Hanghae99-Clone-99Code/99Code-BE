package com.hanghae.code99.Exception;

public class EmptyMultipartFileException extends RuntimeException {
  public EmptyMultipartFileException() {
    super("multipart file is empty");
  }
}
