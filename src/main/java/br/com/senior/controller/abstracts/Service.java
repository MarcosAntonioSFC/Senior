package br.com.senior.controller.abstracts;

import br.com.senior.model.abstracts.AbstractModel;

import org.springframework.data.domain.Page;

/**
 * Interface para disponibilizar os métodos do crud.
 *
 * @param <T> model com extensão.
 */
public interface Service<T extends AbstractModel> {

  Class<T> getEntityClass();

  T create(T model) throws ServiceException;

  T getById(final String id) throws RowNotFoundException, ServiceException;

  Page<T> getAll(Integer page, Integer pageSize) throws ServiceException;

}
