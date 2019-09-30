package br.com.senior.endpoint.cidade.responses;

import br.com.senior.endpoint.abstracts.ResponseWrapper;
import br.com.senior.model.others.EstadoCidade;

import java.util.List;

/**
 * Response para a questão dois.
 */
public class EstadoCidadesResponseWrapper extends ResponseWrapper<List<EstadoCidade>> {

  public EstadoCidadesResponseWrapper(final List<EstadoCidade> data, final int status) {
    super(data, status);
  }

  public EstadoCidadesResponseWrapper(final String message, final int status) {
    super(message, status);
  }

  public EstadoCidadesResponseWrapper(final List<EstadoCidade> data, final int status, final String message) {
    super(data, status, message);
  }
}
