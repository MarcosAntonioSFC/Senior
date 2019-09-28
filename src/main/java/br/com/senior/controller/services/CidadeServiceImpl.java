package br.com.senior.controller.services;

import br.com.senior.controller.abstracts.AbstractService;
import br.com.senior.controller.repository.CidadeRepository;
import br.com.senior.model.Cidade;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Serviço responsável pelas regras de negócio da cidade.
 */
@Service
public class CidadeServiceImpl extends AbstractService<Cidade> implements CidadeService {

  /**
   * Construtor da classe.
   *
   * @param repository CDI.
   */
  @Autowired
  public CidadeServiceImpl(final CidadeRepository repository) {
    super(repository);
  }

}
