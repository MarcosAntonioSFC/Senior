package br.com.senior.controller.services;

import br.com.senior.controller.abstracts.Service;
import br.com.senior.controller.abstracts.ServiceException;
import br.com.senior.model.Cidade;

import org.springframework.web.multipart.MultipartFile;

/**
 * Interface para definição dos métodos que serão acessado externamente.
 */
public interface CidadeService extends Service<Cidade> {

  /**
   * Método para fazer o upload do arquivo.
   *
   * @param file arquivo de upload.
   */
  void upload(final MultipartFile file) throws ServiceException;
}
