package br.com.senior.controller.abstracts;

/**
 * Exceção para dados não encontrados.
 */
public class NotFoundServiceException extends ServiceException {

  private static final String REGISTRO_NOT_FOUND = "Registro não encontrado";

  public NotFoundServiceException(final String message) {
    super(message);
  }

  /**
   * Construtor padrão.
   */
  public NotFoundServiceException() {
    super(REGISTRO_NOT_FOUND);
  }
}
