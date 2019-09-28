package br.com.senior.controller.abstracts;

/**
 * Exceção mais genérica da camada service
 */
public class ServiceException extends Throwable {

  public ServiceException() {
  }

  public ServiceException(final String message) {
    super(message);
  }
}
