package br.com.senior.controller.repository;

import br.com.senior.controller.abstracts.ServiceException;
import br.com.senior.model.Cidade;
import br.com.senior.model.abstracts.AbstractModel;
import com.opencsv.bean.CsvBindByName;
import com.opencsv.bean.CsvCustomBindByName;

import java.lang.reflect.Field;
import java.math.BigDecimal;
import java.text.MessageFormat;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.Path;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Repository;

@Repository
public class CustomCidadeRepositoryImpl implements CustomCidadeRepository {

  private static final String COLUNA_NOT_FOUND = "A coluna consulta é inválida";

  private final CidadeRepository cidadeRepository;

  private List<Class> annotations = Arrays.asList(CsvCustomBindByName.class, CsvBindByName.class);

  @Autowired
  public CustomCidadeRepositoryImpl(CidadeRepository cidadeRepository) {
    this.cidadeRepository = cidadeRepository;
  }

  /**
   * Faz as chamadas aos métodos necessários para construir a consulta dinamicamente.
   *
   * @param column coluna para consulta.
   * @param valor  valor da consulta.
   * @return lista de cidades dentro dos critérios.
   * @throws ServiceException exceção em caso de não encontrar coluna e outros.
   */
  @Override
  public List<Cidade> findByColumnValor(final String column, final String valor) throws ServiceException {
    final Field field = columnValid(column);

    final Specification<Cidade> where = Specification.where(
        (Specification<Cidade>) (root, query, criteriaBuilder) -> {
          final Path<Object> from = resolvePath(root, field.getName());
          final Predicate criteriaType = getCriteriaType(from, criteriaBuilder, valor);
          return criteriaBuilder.and(criteriaType);
        }
    );

    return cidadeRepository.findAll(where);
  }

  /**
   * Devolve o path correto para a consulta.
   *
   * @param root root da consulta.
   * @param name nome do campo.
   * @return path.
   */
  private Path<Object> resolvePath(final Root root, final String name) {
    Path path = root.get(name);
    if (path.getJavaType().getGenericSuperclass().equals(AbstractModel.class)) {
      path = path.get("id");
    }

    return path;
  }

  /**
   * Monsta a criteria.
   *
   * @param from            target.
   * @param criteriaBuilder objeto para construção das condições.
   * @param valor           valor da consulta.
   * @return condição.
   */
  private Predicate getCriteriaType(final Path<Object> from, final CriteriaBuilder criteriaBuilder, final String valor) {
    Predicate predicate = null;
    if (from.getJavaType().equals(Boolean.class)) {
      predicate = criteriaBuilder.equal(from.as(Boolean.class), new Boolean(valor));
    } else if (from.getJavaType().equals(BigDecimal.class)) {
      predicate = criteriaBuilder.equal(from.as(BigDecimal.class), new BigDecimal(valor));
    } else {
      predicate = criteriaBuilder.like(from.as(String.class), MessageFormat.format("%{0}%", valor));
    }

    return predicate;
  }

  /**
   * Efetua a validação se a coluna existe no modelo.
   *
   * @param column coluna para consulta.
   * @return field respectivo à coluna.
   * @throws ServiceException exceção em caso de coluna inválida.
   */
  protected Field columnValid(final String column) throws ServiceException {
    final Optional<Field> existsColumn = Arrays.stream(Cidade.class.getDeclaredFields())
        .filter(x -> Arrays.stream(x.getDeclaredAnnotations()).anyMatch(ann -> annotations.contains(ann.annotationType())))
        .filter(x -> {
          final CsvBindByName csvBindByName = x.getDeclaredAnnotation(CsvBindByName.class);
          if (null != csvBindByName) {
            return csvBindByName.column().equals(column);
          } else {
            final CsvCustomBindByName csvCustomBindByName = x.getDeclaredAnnotation(CsvCustomBindByName.class);
            return csvCustomBindByName.column().equals(column);
          }
        })
        .findFirst();

    if (!existsColumn.isPresent()) {
      throw new ServiceException(COLUNA_NOT_FOUND);
    }

    return existsColumn.get();
  }
}
