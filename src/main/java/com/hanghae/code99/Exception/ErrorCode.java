package com.hanghae.code99.Exception;

import org.springframework.http.HttpStatus;

public enum ErrorCode {
    // File
    AWS_S3_UPLOAD_FAIL(HttpStatus.BAD_REQUEST, "FILE-0001", "AWS S3 File upload fail"),
    AWS_S3_UPLOAD_VALID(HttpStatus.BAD_REQUEST, "FILE-0002", "File validation"),
    EMPTY_MULTIPART_FILE(HttpStatus.BAD_REQUEST,"FILE-0003","Multipart file is empty");

    HttpStatus status;
    String code;
    String message;

    public HttpStatus getStatus() {
        return status;
    }

    public String getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }

    ErrorCode(HttpStatus status, String code, String message) {
        this.status = status;
        this.code = code;
        this.message = message;
    }

}
