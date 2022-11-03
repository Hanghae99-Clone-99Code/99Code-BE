package com.hanghae.code99.Exception;

public class FileConvertException extends RuntimeException {

  public FileConvertException() {
    super("fail convert multipartfile to file");
  }
}
