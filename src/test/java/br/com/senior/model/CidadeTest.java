package br.com.senior.model;

import com.opencsv.bean.CsvBindByName;
import com.opencsv.bean.CsvCustomBindByName;

import java.lang.reflect.Field;
import java.text.MessageFormat;
import java.util.HashMap;
import java.util.Map;

import org.junit.Assert;
import org.junit.Test;
import org.springframework.util.ReflectionUtils;

public class CidadeTest {

  static final HashMap<String, String> fieldsMap = new HashMap<>();
  static final String messageAnnotationFail = "O campo {0} não possui annotation para conversão do csv.";
  static final String messageColumnFail = "O campo {0} deve estar mapeado para {1}.";
  static final String messageColumnNotFound = "O campo {0} não foi encontrado.";

  static {
    fieldsMap.put("id", "ibge_id");
    fieldsMap.put("nome", "name");
    fieldsMap.put("capital", "capital");
    fieldsMap.put("latitude", "lat");
    fieldsMap.put("longitude", "lon");
    fieldsMap.put("noAccents", "no_accents");
    fieldsMap.put("nomeAlternativo", "alternative_names");
    fieldsMap.put("microRegiao", "microregion");
    fieldsMap.put("mesoRegiao", "mesoregion");
  }

  @Test
  public void annotationsCsv() {
    for (final Map.Entry<String, String> fieldMapAnnotation : fieldsMap.entrySet()) {
      final Field field = ReflectionUtils.findField(Cidade.class, fieldMapAnnotation.getKey());
      if (null == field) {
        Assert.fail(MessageFormat.format(messageColumnNotFound, fieldMapAnnotation.getKey()));
      }
      final CsvBindByName annotation = field.getAnnotation(CsvBindByName.class);
      if (null == annotation) {
        Assert.fail(MessageFormat.format(messageAnnotationFail, field.getName()));
      }

      Assert.assertEquals(
          MessageFormat.format(messageColumnFail, field.getName(), fieldMapAnnotation.getValue()),
          annotation.column(),
          fieldMapAnnotation.getValue()
      );
    }
  }

  @Test
  public void annotationUf() {
    final String fieldName = "estado";
    final String csvfieldName = "uf";

    final Field field = ReflectionUtils.findField(Cidade.class, fieldName);
    if (null == field) {
      Assert.fail(MessageFormat.format(messageColumnNotFound, fieldName));
    }

    final CsvCustomBindByName annotation = field.getAnnotation(CsvCustomBindByName.class);
    if (null == annotation) {
      Assert.fail(MessageFormat.format(messageAnnotationFail, field.getName()));
    }

    Assert.assertEquals(
        MessageFormat.format(messageColumnFail, field.getName(), csvfieldName),
        annotation.column(),
        csvfieldName
    );

    Assert.assertEquals(
        MessageFormat.format(messageColumnFail, field.getName(), csvfieldName),
        annotation.converter(),
        EstadoConverterCsv.class
    );
  }
}