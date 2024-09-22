package com.chat.media_service.exception;

public enum ErrorCode {

  MEDIA_ERROR1("Error when write bytes to file", 500),
  MEDIA_ERROR2("Error when uploading resource to Cloudinary", 500);


  private final String errorMessage;
  private final int httpStatus;

  ErrorCode(String errorMessage, int httpStatus) {
    this.errorMessage = errorMessage;
    this.httpStatus = httpStatus;
  }

  public String getErrorMessage() {
    return errorMessage;
  }

  public int getHttpStatus() {
    return httpStatus;
  }
}
