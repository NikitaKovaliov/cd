package by.kovaliov.cd.exception;

public enum ExceptionType {
  RESOURCE_NOT_FOUND("Requesting resource not found", 404),
  RESOURCE_ALREADY_EXISTS("Resource with such name already exists", 400);

  private final String exceptionMessage;
  private final int httpCode;

  ExceptionType(String exceptionMessage, int httpCode) {
    this.exceptionMessage = exceptionMessage;
    this.httpCode = httpCode;
  }

  public String getExceptionMessage() {
    return exceptionMessage;
  }

  public int getHttpCode() {
    return httpCode;
  }
}