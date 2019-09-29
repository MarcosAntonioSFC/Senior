package br.com.senior.controller.others;

import br.com.senior.model.Cidade;
import com.google.common.collect.Lists;
import com.opencsv.bean.CsvToBean;
import com.opencsv.bean.CsvToBeanBuilder;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;

/**
 * Classe utilitária para a manipulação dos dados.
 */
public class CsvUtils {

  /**
   * Método que fará a conversão do byte[] para o csv
   *
   * @throws IOException
   * @return
   */
  public static ArrayList<Cidade> read(final InputStream inputStream) throws IOException {
    Reader reader = new BufferedReader(new InputStreamReader(inputStream, StandardCharsets.UTF_8));

    CsvToBean<Cidade> csvToBean = new CsvToBeanBuilder(reader)
        .withType(Cidade.class)
        .withIgnoreLeadingWhiteSpace(true)
        .build();

    return Lists.newArrayList(csvToBean.iterator());
  }

}
