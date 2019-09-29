package br.com.senior.endpoint.abstracts;

import java.io.Serializable;

/**
 * Abstração para retor dos endpoints
 *
 * @param <T>
 */
public abstract class AbstractResponseWrapper<T> implements Serializable {

  /**
   * Dado própriamente dito.
   */
  private T data;

  /**
   *
   */
  private String message;

  /**
   * Http code
   *
   * @see org.springframework.http.HttpStatus
   */
  private int status;

  public AbstractResponseWrapper(T data, final int status) {
    this.data = data;
    this.status = status;
  }

  public AbstractResponseWrapper(final String message, final int status) {
    this.message = message;
    this.status = status;
  }

  public AbstractResponseWrapper(final T data, final int status, final String message) {
    this(data, status);
    this.message = message;
  }

  public T getData() {
    return data;
  }

  public void setData(final T data) {
    this.data = data;
  }

  public int getStatus() {
    return status;
  }

  public void setStatus(final int status) {
    this.status = status;
  }

  public String getMessage() {
    return message;
  }

  public void setMessage(final String message) {
    this.message = message;
  }
}
