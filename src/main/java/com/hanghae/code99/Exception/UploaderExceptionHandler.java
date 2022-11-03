package com.hanghae.code99.Exception;

import com.hanghae.code99.controller.response.ResponseDto;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class UploaderExceptionHandler {

  @ResponseStatus(HttpStatus.BAD_REQUEST)
  @ExceptionHandler(EmptyMultipartFileException.class)
  public ResponseDto<?> handle(EmptyMultipartFileException ex) {
    return ResponseDto.fail(
        "EMPTY",
        "multipart file is empty"
    );
  }

  @ResponseStatus(HttpStatus.BAD_REQUEST)
  @ExceptionHandler(FileConvertException.class)
  public ResponseDto<?> handle(FileConvertException ex) {
    return ResponseDto.fail(
        "CONVERT_FAIL",
        "fail convert multipart to file"
    );
  }

  @ResponseStatus(HttpStatus.BAD_REQUEST)
  @ExceptionHandler(RemoveFileException.class)
  public ResponseDto<?> handle(RemoveFileException ex) {
    return ResponseDto.fail(
        "REMOVE_FAIL",
        "fail to file remove"
    );
  }
}
