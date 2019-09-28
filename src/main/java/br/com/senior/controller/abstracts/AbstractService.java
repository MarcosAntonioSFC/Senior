package br.com.senior.controller.abstracts;

import br.com.senior.controller.others.CommonRepository;
import br.com.senior.model.abstracts.AbstractModel;

/**
 * Abstração para o serviço.
 *
 * @param <T> objeto generics indicando o model.
 */
public abstract class AbstractService<T extends AbstractModel> implements Service<T> {

  /**
   * Instancia do repository que deverá ser passada pela subclasse.
   */
  protected CommonRepository<T> repository;

  /**
   * Construtor
   *
   * @param repository repositório injetado na subclasse.
   */
  public AbstractService(CommonRepository<T> repository) {
    this.repository = repository;
  }

  /**
   * Devolve o repositório para a subclasse trabalhar.
   *
   * @return repository.
   */
  public CommonRepository<T> getRepository() {
    return repository;
  }
}
