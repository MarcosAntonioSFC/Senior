package br.com.senior.endpoint.abstracts;

import java.io.Serializable;

/**
 * Abstração para retor dos endpoints
 *
 * @param <T> T que seja serializavel.
 */
public abstract class AbstractResponseWrapper<T extends Serializable> implements Serializable {

  /**
   * Dado própriamente dito.
   */
  private T data;

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
}
