package br.com.senior.controller.services;

import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.spy;

import br.com.senior.controller.abstracts.ServiceException;
import br.com.senior.controller.repository.CidadeRepository;
import br.com.senior.model.Estado;
import br.com.senior.model.others.EstadoCidade;

import java.io.IOException;
import java.io.InputStream;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import org.junit.Assert;
import org.junit.Test;
import org.mockito.Mockito;
import org.springframework.web.multipart.MultipartFile;

/**
 * Classe com teste unitário dos serviços principais da cidade.
 */
public class CidadeServiceImplTest {

  /**
   * Garante a chamada de todos os métodos e garante o processo do método.
   *
   * @throws IOException      exceção de leitura.
   * @throws ServiceException exceção do serviço.
   */
  @Test
  public void upload() throws IOException, ServiceException {
    final InputStream resource = getClass().getClassLoader().getResourceAsStream("cidadesTestes.csv");
    final MultipartFile multipartFile = Mockito.mock(MultipartFile.class);
    final CidadeRepository repository = Mockito.mock(CidadeRepository.class);
    final EstadoService estadoService = Mockito.mock(EstadoService.class);

    //Garanti a chamada do método save com todos os estados presentes no arquivo.
    final List<String> estados = Arrays.asList("RO", "AC", "AM", "RR", "PA", "AP", "AP", "TO", "MA");
    Mockito.doAnswer(answer -> {
      final List<Estado> ufToSave = answer.getArgument(0);
      Assert.assertTrue(estados.containsAll(ufToSave.stream().map(Estado::getId).collect(Collectors.toList())));
      return ufToSave;
    }).when(estadoService).save(Mockito.anyList());

    Mockito.doReturn(resource).when(multipartFile).getInputStream();
    CidadeServiceImpl service = new CidadeServiceImpl(repository, null, estadoService);
    service = spy(service);
    service.upload(multipartFile);

    Mockito.verify(estadoService, Mockito.times(1)).save(Mockito.anyList());
    Mockito.verify(service, Mockito.times(1)).save(Mockito.anyList());
  }

  @Test
  public void getEstadoCidadeMenorMaior() {
    CidadeServiceImpl service = new CidadeServiceImpl(null, null, null);
    service = spy(service);

    final List<EstadoCidade> all = doReturn(
        Arrays.asList(
            new EstadoCidade("MEIO", 2),
            new EstadoCidade("MAIOR", 3),
            new EstadoCidade("MENOR", 1)
        )
    ).when(service).getCidadeEstados();

    final List<EstadoCidade> result = service.getEstadoCidadeMenorMaior();
    Assert.assertTrue("Menor não está presente", result.stream().anyMatch(x -> x.getQuantidade() == 1));
    Assert.assertTrue("Maior não está presente", result.stream().anyMatch(x -> x.getQuantidade() == 3));
  }
}