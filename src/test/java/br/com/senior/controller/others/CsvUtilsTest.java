package br.com.senior.controller.others;

import br.com.senior.model.Cidade;

import java.io.IOException;
import java.io.InputStream;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Optional;

import org.assertj.core.util.Strings;
import org.junit.Assert;
import org.junit.Test;

/**
 * Classe para testar a conversão do csv.
 */
public class CsvUtilsTest {

  @Test
  public void readCsvOpen() throws IOException {
    final InputStream resource = getClass().getClassLoader().getResourceAsStream("cidadesTestes.csv");
    final ArrayList<Cidade> cidades = CsvUtils.read(resource);
    Assert.assertNotNull(cidades);
    Assert.assertEquals("O arquivo contém 9 linhas", 9, cidades.size());
    final Optional<Cidade> cidadeOpt = cidades.stream().filter(x -> x.getId().equals("1100015")).findFirst();
    if (!cidadeOpt.isPresent()) {
      Assert.fail("1100015 não encontrado, mas contém no arquivo.");
    }

    final Cidade cidade = cidadeOpt.get();
    Assert.assertEquals("RO", cidade.getEstado().getId());
    Assert.assertEquals("Alta Floresta D'Oeste", cidade.getNome());
    Assert.assertFalse(cidade.getCapital());
    Assert.assertEquals(new BigDecimal("-61.9998238963"), cidade.getLongitude());
    Assert.assertEquals(new BigDecimal("-11.9355403048"), cidade.getLatitude());
    Assert.assertEquals("Alta Floresta D'Oeste", cidade.getNoAccents());
    Assert.assertTrue(Strings.isNullOrEmpty(cidade.getNomeAlternativo()));
    Assert.assertEquals("Cacoal", cidade.getMicroRegiao());
    Assert.assertEquals("Leste Rondoniense", cidade.getMesoRegiao());
  }
}
