package br.com.senior.endpoint.cidade.responses;

import br.com.senior.endpoint.abstracts.AbstractResponseWrapper;
import br.com.senior.model.Cidade;

import java.util.List;

/**
 * Response padrão para o model cidade.
 */
public class CidadesResponseWrapper extends AbstractResponseWrapper<List<Cidade>> {

  /**
   * Construtor padrão.
   *
   * @param data   objeto.
   * @param status status da resposta (http code).
   */
  public CidadesResponseWrapper(final List<Cidade> data, final int status) {
    super(data, status);
  }

  public CidadesResponseWrapper(final String message, final int status) {
    super(message, status);
  }

  public CidadesResponseWrapper(final List<Cidade> data, final int status, final String message) {
    super(data, status, message);
  }

}
