package br.com.senior.controller.repository;

import br.com.senior.controller.others.CommonRepository;
import br.com.senior.model.Cidade;
import br.com.senior.model.others.EstadoCidade;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

/**
 * Repositório da cidade.
 */
@Repository
public interface CidadeRepository extends CommonRepository<Cidade>, CustomCidadeRepository<Cidade> {

  List<Cidade> findCidadeByCapitalOrderByNome(final Boolean capital);

  @Query("select new br.com.senior.model.others.EstadoCidade(uf.sigla, count(*) as contagem) "
      + "  from br.com.senior.model.Cidade city "
      + "  join city.estado uf "
      + " group by uf.id "
      + " order by contagem desc")
  List<EstadoCidade> findEstadoCidades();

  @Query("select city.nome from br.com.senior.model.Cidade city where city.estado.id = :uf")
  List<String> findByEstado(final String uf);

  @Query("select city from br.com.senior.model.Cidade city where city.capital = true and city.estado.id = :uf")
  Cidade findCapitalByEstado(final String uf);
}
