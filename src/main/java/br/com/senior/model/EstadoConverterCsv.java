package br.com.senior.model;

import com.opencsv.bean.AbstractBeanField;
import com.opencsv.exceptions.CsvConstraintViolationException;
import com.opencsv.exceptions.CsvDataTypeMismatchException;

/**
 * Customização da leitura do estado, pois será convertido para um tipo complexo.
 */
public class EstadoConverterCsv extends AbstractBeanField<Estado> {

  /**
   * Sobrescritad do método para a conversão customizada.
   *
   * @param value valor recebido (lido do csv).
   * @return objeto do tipo T
   * @throws CsvDataTypeMismatchException    exceção.
   * @throws CsvConstraintViolationException exceção.
   */
  @Override
  protected Estado convert(final String value) throws CsvDataTypeMismatchException, CsvConstraintViolationException {
    return EstadoBuilder.newInstance().withId(value).withSigla(value).build();
  }
}
