package br.com.senior.controller.services;

import br.com.senior.controller.abstracts.AbstractService;
import br.com.senior.controller.repository.EstadoRepository;
import br.com.senior.model.Estado;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Serviço responsável pelas regras de negócio da cidade.
 */
@Service
public class EstadoServiceImpl extends AbstractService<Estado, EstadoRepository> implements EstadoService {

  /**
   * Construtor da classe.
   *
   * @param repository CDI.
   */
  @Autowired
  public EstadoServiceImpl(final EstadoRepository repository) {
    super(repository);
  }

}
