package br.com.senior.controller.repository;

import br.com.senior.controller.abstracts.ServiceException;

import org.junit.Assert;
import org.junit.Test;

public class CustomCidadeRepositoryImplTest {

  @Test
  public void columnValid() throws ServiceException {
    final CustomCidadeRepositoryImpl repository = new CustomCidadeRepositoryImpl(null, null);

    try {
      Assert.assertNotNull("Coluna não encontrada", repository.columnValid("ibge_id"));
      Assert.assertNotNull("Coluna não encontrada", repository.columnValid("uf"));
    } catch (ServiceException e) {
      Assert.fail("Existe o campo na classe.");
      throw e;
    }

    try {
      Assert.assertNull("Coluna não existe", repository.columnValid("xpto"));
      Assert.fail("Coluna não existe");
    } catch (ServiceException e) {
      throw e;
    }
  }

}