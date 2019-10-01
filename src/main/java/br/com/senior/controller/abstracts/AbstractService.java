package br.com.senior.controller.abstracts;

import br.com.senior.controller.others.CommonRepository;
import br.com.senior.model.abstracts.AbstractModel;
import com.google.common.base.Strings;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
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
public abstract class AbstractService<T extends AbstractModel, R extends CommonRepository<T>> implements Service<T> {

  private static final String DADO_NAO_INFORMADO = "Dados não informados.";

  private static final String IDENTIFICADOR_NOT_FOUND = "Identificador não informado";
  /**
   * Instancia do repository que deverá ser passada pela subclasse.
   */
  protected R repository;

  /**
   * Construtor
   *
   * @param repository repositório injetado na subclasse.
   */
  public AbstractService(R repository) {
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
   * Método abstrato para a busca de dados baseando-se no identificador.
   *
   * @param id identificador do registro.
   * @return objeto encontrado.
   * @throws NotFoundServiceException exceção caso não encontre o respectivo registro.
   */
  @Override
  public T getById(final String id) throws ServiceException {
    if (Strings.isNullOrEmpty(id)) {
      throw new ServiceException(IDENTIFICADOR_NOT_FOUND);
    }
    final Optional<T> byId = getRepository().findById(id);
    if (!byId.isPresent()) {
      throw new NotFoundServiceException();
    }

    return byId.get();
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
   * #7 - Método abstrato para adicionar uma nova entidade.
   *
   * @param entity entidade.
   * @return entidade persistida.
   */
  @Override
  public T add(final T entity) throws br.com.senior.controller.abstracts.ServiceException {
    if (null == entity) {
      throw new ServiceException(DADO_NAO_INFORMADO);
    }

    validationEntity(entity);
    beforeAdd(entity);
    return save(entity);
  }

  /**
   * Método para adicionar e validar valores na entidade pela subclasse.
   *
   * @param entity entidade.
   */
  @Override
  public void beforeAdd(final T entity) throws br.com.senior.controller.abstracts.ServiceException {
    //
  }

  /**
   * #8 - Permite a exclusao de um registro.
   *
   * @param id identificador do registro.
   */
  @Override
  public void delete(final String id) throws ServiceException {
    final T entity = getById(id);
    getRepository().delete(entity);
  }

  /**
   * Devolve o repositório para a subclasse trabalhar.
   *
   * @return repository.
   */
  public R getRepository() {
    return repository;
  }
}
