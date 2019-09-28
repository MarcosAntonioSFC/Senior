package br.com.senior.controller.others;

import java.io.IOException;

import org.junit.Assert;
import org.junit.Test;

/**
 * Classe para testar a conversão do csv.
 */
public class CsvUtilsTest {

  @Test
  public void readCsvOpen() throws IOException {
    CsvUtils.read();
    Assert.fail("Testar a conversão");
  }
}