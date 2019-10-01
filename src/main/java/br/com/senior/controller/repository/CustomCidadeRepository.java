package br.com.senior.controller.repository;

import br.com.senior.controller.abstracts.ServiceException;
import br.com.senior.model.Cidade;

import java.util.List;

public interface CustomCidadeRepository {

  List<Cidade> findByColumnValor(final String column, final String valor) throws ServiceException;

  Long countByColumn(final String column) throws ServiceException;
}
