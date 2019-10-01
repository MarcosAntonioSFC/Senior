package br.com.senior.controller.abstracts;

import br.com.senior.model.abstracts.AbstractModel;

import java.util.List;

/**
 * Interface comum para os serviços da aplicação.
 *
 * @param <T> model com extensão abstractmodel.
 */
public interface Service<T extends AbstractModel> {

  T save(final T toSave);

  List<T> save(final List<T> toSave);

  T getById(final String id) throws ServiceException;

  T add(final T entity) throws ServiceException;

  void beforeAdd(T entity) throws ServiceException;

  void delete(final String id) throws ServiceException;
}
