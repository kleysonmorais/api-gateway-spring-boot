package br.com.senior.gateway.exception;

import java.io.Serializable;

public class ErrorDetails implements Serializable {

  private long timestamp;
  private int status;
  private String error;
  private String message;
  private String path;

  public ErrorDetails() {}

  public ErrorDetails(long timestamp, int status, String error, String message, String path) {
    this.timestamp = timestamp;
    this.status = status;
    this.error = error;
    this.message = message;
    this.path = path;
  }

  public long getTimestamp() {
    return timestamp;
  }

  public int getStatus() {
    return status;
  }

  public String getError() {
    return error;
  }

  public String getMessage() {
    return message;
  }

  public String getPath() {
    return path;
  }
}
