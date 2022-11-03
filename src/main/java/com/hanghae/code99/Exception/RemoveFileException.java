package com.hanghae.code99.Exception;

public class RemoveFileException extends RuntimeException {
  public RemoveFileException() {
    super("fail to remove target file");
  }
}
