package br.com.senior.controller.repository;

import br.com.senior.controller.abstracts.ServiceException;
import br.com.senior.model.Cidade;
import com.opencsv.bean.CsvBindByName;
import com.opencsv.bean.CsvCustomBindByName;

import java.util.Arrays;
import java.util.List;

import javax.persistence.EntityManager;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class CustomCidadeRepositoryImpl implements CustomCidadeRepository {

  private static final String COLUNA_NOT_FOUND = "A coluna consulta é inválida";

  private final EntityManager entityManager;

  private List<Class> annotations = Arrays.asList(CsvCustomBindByName.class, CsvBindByName.class);

  @Autowired
  public CustomCidadeRepositoryImpl(EntityManager entityManager) {
    this.entityManager = entityManager;
  }

  @Override
  public List<Cidade> findByColumnValor(final String column, final String valor) throws ServiceException {
    columnValid(column);

    return null;
  }

  /**
   * Efetua a validação se a coluna existe no modelo.
   *
   * @param column coluna para consulta.
   * @throws ServiceException exceção em caso de coluna inválida.
   */
  protected void columnValid(final String column) throws ServiceException {
    final boolean existsColumn = Arrays.stream(Cidade.class.getDeclaredFields())
        .filter(x -> Arrays.stream(x.getDeclaredAnnotations()).anyMatch(ann -> annotations.contains(ann.annotationType())))
        .anyMatch(x -> {
          final CsvBindByName csvBindByName = x.getDeclaredAnnotation(CsvBindByName.class);
          if (null != csvBindByName) {
            return csvBindByName.column().equals(column);
          } else {
            final CsvCustomBindByName csvCustomBindByName = x.getDeclaredAnnotation(CsvCustomBindByName.class);
            return csvCustomBindByName.column().equals(column);
          }
        });

    if (!existsColumn) {
      throw new ServiceException(COLUNA_NOT_FOUND);
    }
  }
}
