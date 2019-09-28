package br.com.senior.controller.others;

import br.com.senior.model.Cidade;
import com.google.common.collect.Lists;
import com.opencsv.bean.CsvToBean;
import com.opencsv.bean.CsvToBeanBuilder;

import java.io.IOException;
import java.io.Reader;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;

/**
 * Classe utilitária para a manipulação dos dados.
 */
public class CsvUtils {

  /**
   * Método que fará a conversão do byte[] para o csv
   *
   * @throws IOException
   */
  public static void read() throws IOException {
    Reader reader = Files.newBufferedReader(Paths.get("cidadesTestes.csv"));

    CsvToBean<Cidade> csvToBean = new CsvToBeanBuilder(reader)
        .withType(Cidade.class)
        .withIgnoreLeadingWhiteSpace(true)
        .build();

    final ArrayList<Cidade> cidades = Lists.newArrayList(csvToBean.iterator());
  }

}
