package br.com.senior.endpoint.cidade;

import br.com.senior.controller.abstracts.Service;
import br.com.senior.controller.abstracts.ServiceException;
import br.com.senior.endpoint.abstracts.ResponseWrapper;
import br.com.senior.model.abstracts.AbstractModel;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

/**
 * Abstração para endpoint.
 *
 * @param <T> objeto que extende de abstractModel
 * @param <S> serviço que extende do serviço em commum
 */
public abstract class AbstractEndpoint<T extends AbstractModel, S extends Service<T>> {

  private static final String ACAO_SUCESS = "Ação efetuada com sucesso.";

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
   * #5 - Método abstrato para buscar registro pelo identificador.
   *
   * @return retorna o objeto encontrado de acordo com o identificador.
   */
  @GetMapping(
      path = "/{id}/",
      produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE}
  )
  public ResponseWrapper<T> getById(@PathVariable("id") final String id) {
    AbstractModel model = null;
    try {
      model = service.getById(id);
    } catch (Throwable e) {
      return new ResponseWrapper<T>(e.getMessage(), HttpStatus.CONFLICT.value());
    }
    return new ResponseWrapper(model, HttpStatus.OK.value());
  }

  /**
   * #7 - Método abstrato para adição de uma nova entidade do tipo T.
   *
   * @param entity entidade com dados para persistencia.
   * @return entidade persistida.
   */
  @PostMapping(
      path = "/",
      produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE},
      consumes = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE}
  )
  public ResponseWrapper add(@RequestBody T entity) {
    try {
      return new ResponseWrapper(getService().add(entity), HttpStatus.OK.value());
    } catch (ServiceException e) {
      return new ResponseWrapper(e.getMessage(), HttpStatus.CONFLICT.value());
    }
  }

  /**
   * #8 - Permite deletar um registro do tipo T
   *
   * @param id identificador do registro.
   * @return mensagem de sucesso.
   */
  @DeleteMapping(
      path = "/{id}/",
      produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE}
  )
  public ResponseWrapper delete(@PathVariable("id") final String id) {
    try {
      getService().delete(id);
      return new ResponseWrapper(ACAO_SUCESS, HttpStatus.OK.value());
    } catch (Exception | ServiceException e) {
      return new ResponseWrapper(e.getMessage(), HttpStatus.CONFLICT.value());
    }
  }

  public S getService() {
    return service;
  }
}
