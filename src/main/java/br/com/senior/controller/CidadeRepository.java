package br.com.senior.controller;

import br.com.senior.controller.others.CommonRepository;
import br.com.senior.model.Cidade;

import org.springframework.stereotype.Repository;

/**
 * Repositório da cidade.
 */
@Repository
public interface CidadeRepository extends CommonRepository<Cidade> {
}
