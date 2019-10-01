package br.com.senior.controller.services;

import br.com.senior.controller.abstracts.Service;
import br.com.senior.controller.abstracts.ServiceException;
import br.com.senior.model.Cidade;
import br.com.senior.model.others.EstadoCidade;

import java.util.List;

import org.springframework.web.multipart.MultipartFile;

/**
 * Interface para definição dos métodos que serão acessado externamente.
 */
public interface CidadeService extends Service<Cidade> {

  List<Cidade> upload(final MultipartFile file) throws ServiceException;

  List<Cidade> getCapitais();

  List<EstadoCidade> getEstadoCidadeMenorMaior();

  List<EstadoCidade> getCidadeEstados();

  List<String> findByEstado(final String uf) throws ServiceException;

  List<Cidade> findByColumn(final String column, final String valor) throws ServiceException;

  Long countByColumn(final String column) throws ServiceException;

  long countAll();
}
