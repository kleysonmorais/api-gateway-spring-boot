package br.com.senior.gateway.exception;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class ControllerHandleException extends ResponseEntityExceptionHandler {

  @Override
  protected ResponseEntity<Object> handleHttpMessageNotReadable(
      HttpMessageNotReadableException ex,
      HttpHeaders headers,
      HttpStatus status,
      WebRequest request) {

    ErrorDetails errorDetails =
        new ErrorDetails(
            System.currentTimeMillis(),
            status.value(),
            "Malformed JSON",
            ex.getMessage(),
            request.getContextPath());
    return new ResponseEntity<>(errorDetails, headers, status);
  }

  @Override
  protected ResponseEntity<Object> handleExceptionInternal(
      Exception ex, Object body, HttpHeaders headers, HttpStatus status, WebRequest request) {

    ErrorDetails errorDetails =
        new ErrorDetails(
            System.currentTimeMillis(),
            status.value(),
            "Internal Exception",
            ex.getMessage(),
            request.getContextPath());

    return new ResponseEntity<>(errorDetails, headers, status);
  }

  @ExceptionHandler(RequestException.class)
  protected ResponseEntity<?> handleRequestException(RequestException ex, WebRequest request) {

    ErrorDetails errorDetails =
        new ErrorDetails(
            System.currentTimeMillis(),
            HttpStatus.UNPROCESSABLE_ENTITY.value(),
            "Request invalid",
            ex.getMessage(),
            request.getContextPath());

    return new ResponseEntity<>(errorDetails, HttpStatus.UNPROCESSABLE_ENTITY);
  }
}
