package br.com.senior.endpoint.cidade;

import br.com.senior.controller.abstracts.Service;
import br.com.senior.endpoint.abstracts.ResponseWrapper;
import br.com.senior.model.abstracts.AbstractModel;

import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

/**
 * Abstração para endpoint.
 *
 * @param <T> objeto que extende de abstractModel
 * @param <S> serviço que extende do serviço em commum
 */
public abstract class AbstractEndpoint<T extends AbstractModel, S extends Service<T>> {

  /**
   * Serviço recebido pela subclasse.
   */
  private final S service;

  /**
   * Construtor do endpoint.
   *
   * @param service serviço.
   */
  public AbstractEndpoint(S service) {
    this.service = service;
  }

  /**
   * Método abstrato para buscar registro pelo identificador.
   *
   * @return retorna o objeto encontrado de acordo com o identificador.
   */
  @GetMapping(
      path = "/{id}",
      produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE}
  )
  public ResponseWrapper<T> getById(@PathVariable("id") final String id) {
    AbstractModel model = null;
    try {
      model = service.getById(id);
    } catch (Throwable e) {
      return new ResponseWrapper<T>(e.getMessage(), 409);
    }
    return new ResponseWrapper(model, 200);
  }

  public S getService() {
    return service;
  }
}
