package by.kovaliov.cd.exception;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class ServerExceptionHandler {

  private static final String MESSAGE_PROPERTY = "message";
  private static final String STATUS_CODE_PROPERTY = "statusCode";
  private static final String STATUS_MESSAGE_PROPERTY = "statusMessage";

  @ExceptionHandler(ServerException.class)
  private ResponseEntity<Map<String, String>> handleServerException(ServerException e) {
    return buildResponse(e.getMessage(), e.getExceptionType().getHttpCode());
  }

  @ExceptionHandler(MethodArgumentNotValidException.class)
  private ResponseEntity<Map<String, String>> handleMethodArgumentNotValidException(MethodArgumentNotValidException e) {
    List<FieldError> fieldErrors = e.getBindingResult().getFieldErrors();
    StringBuilder stringBuilder = new StringBuilder();
    for (FieldError fieldError : fieldErrors) {
      stringBuilder.append(String.format("%s: %s;", fieldError.getField(), fieldError.getDefaultMessage()));
    }
    return buildResponse(stringBuilder.toString(), HttpStatus.BAD_REQUEST.value());
  }

  private ResponseEntity<Map<String, String>> buildResponse(String message, int status) {
    Map<String, String> responseProperties = new HashMap<>();
    responseProperties.put(MESSAGE_PROPERTY, message);
    responseProperties.put(STATUS_CODE_PROPERTY, String.valueOf(status));
    responseProperties.put(STATUS_MESSAGE_PROPERTY, HttpStatus.valueOf(status).getReasonPhrase());
    return ResponseEntity
        .status(status)
        .body(responseProperties);
  }
}