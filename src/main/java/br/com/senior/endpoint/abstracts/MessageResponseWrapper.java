package br.com.senior.endpoint.abstracts;

/**
 * Response genérico, apenas para retornar mensagens.
 */
public class MessageResponseWrapper extends AbstractResponseWrapper<String> {

  public MessageResponseWrapper(final String data, final int status) {
    super(data, status);
  }
}
