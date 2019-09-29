package br.com.senior.controller.abstracts;

import br.com.senior.controller.others.CommonRepository;
import br.com.senior.model.abstracts.AbstractModel;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import javax.validation.Validation;
import javax.validation.ValidatorFactory;

import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

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
   * Método abstrato para salva uma entidade.
   *
   * @param toSave objeto que será salvo.
   * @return objeto persistido.
   */
  @Override
  @Transactional(rollbackFor = Exception.class, propagation = Propagation.REQUIRES_NEW, isolation = Isolation.READ_UNCOMMITTED)
  public T save(final T toSave) {
    validationEntity(toSave);
    return getRepository().save(toSave);
  }

  /**
   * Método abstrato para salva uma entidade.
   *
   * @param toSave objeto que será salvo.
   * @return objeto persistido.
   */
  @Override
  @Transactional(rollbackFor = Exception.class, propagation = Propagation.REQUIRES_NEW, isolation = Isolation.READ_UNCOMMITTED)
  public List<T> save(final List<T> toSave) {
    final List<T> saves = new ArrayList<>();
    for (final T entity : toSave) {
      saves.add(getRepository().save(entity));
    }
    return saves;
  }

  /**
   * Efetua a chama de validação do JPA.
   *
   * @param entity model a ser validade
   */
  public void validationEntity(final T entity) {
    final ValidatorFactory validatorFactory = Validation.buildDefaultValidatorFactory();
    final Set<ConstraintViolation<T>> validate = validatorFactory.getValidator().validate(entity);
    if (!validate.isEmpty()) {
      throw new ConstraintViolationException(validate);
    }
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
