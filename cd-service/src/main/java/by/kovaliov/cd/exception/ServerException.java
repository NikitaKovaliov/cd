package by.kovaliov.cd.exception;

public class ServerException extends RuntimeException {

  private final ExceptionType exceptionType;

  public ServerException(ExceptionType exceptionType) {
    super(exceptionType.getExceptionMessage());
    this.exceptionType = exceptionType;
  }

  public ExceptionType getExceptionType() {
    return exceptionType;
  }
}