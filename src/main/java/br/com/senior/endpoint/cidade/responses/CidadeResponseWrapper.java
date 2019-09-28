package br.com.senior.endpoint.cidade.responses;

import br.com.senior.endpoint.abstracts.AbstractResponseWrapper;
import br.com.senior.model.Cidade;

/**
 * Response padrão para o model cidade.
 */
public class CidadeResponseWrapper extends AbstractResponseWrapper<Cidade> {

  /**
   * Construtor padrão.
   *
   * @param data   objeto.
   * @param status status da resposta (http code).
   */
  public CidadeResponseWrapper(final Cidade data, final int status) {
    super(data, status);
  }
}
