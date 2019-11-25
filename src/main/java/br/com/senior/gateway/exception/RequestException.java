package br.com.senior.gateway.exception;

public class RequestException extends RuntimeException {

  public RequestException(String message) {
    super(message);
  }
}
