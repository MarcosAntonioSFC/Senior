package br.com.senior.controller.abstracts;

import br.com.senior.model.abstracts.AbstractModel;

import java.util.List;

/**
 * Interface comum para os serviços da aplicação.
 *
 * @param <T> model com extensão abstractmodel.
 */
public interface Service<T extends AbstractModel> {

  T save(T toSave);

  List<T> save(List<T> toSave);
}
