package br.com.senior.model;

import com.opencsv.exceptions.CsvConstraintViolationException;
import com.opencsv.exceptions.CsvDataTypeMismatchException;

import org.junit.Assert;
import org.junit.Test;

/**
 * Classe para testar o conversor.
 */
public class EstadoConverterCsvTest {

  final EstadoConverterCsv estadoConverterCsv = new EstadoConverterCsv();

  @Test
  public void convert() throws CsvDataTypeMismatchException, CsvConstraintViolationException {
    final Estado uf = estadoConverterCsv.convert("SP");
    Assert.assertNotNull(uf);
    Assert.assertEquals("SP", uf.getId());
    Assert.assertEquals("SP", uf.getSigla());
  }

  @Test
  public void convertNull() throws CsvDataTypeMismatchException, CsvConstraintViolationException {
    final Estado ufNull = estadoConverterCsv.convert(null);
    Assert.assertNotNull(ufNull);
    Assert.assertNull(ufNull.getId());
    Assert.assertNull(ufNull.getSigla());
  }

  @Test
  public void convertEmpty() throws CsvDataTypeMismatchException, CsvConstraintViolationException {
    final Estado ufNull = estadoConverterCsv.convert("");
    Assert.assertNotNull(ufNull);
    Assert.assertEquals("", ufNull.getId());
    Assert.assertEquals("", ufNull.getSigla());
  }
}