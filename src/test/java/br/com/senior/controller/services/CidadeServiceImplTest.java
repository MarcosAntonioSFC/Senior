package br.com.senior.controller.services;

import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.spy;

import br.com.senior.controller.abstracts.NotFoundServiceException;
import br.com.senior.controller.abstracts.ServiceException;
import br.com.senior.controller.repository.CidadeRepository;
import br.com.senior.model.Cidade;
import br.com.senior.model.Estado;
import br.com.senior.model.others.EstadoCidade;

import java.io.IOException;
import java.io.InputStream;
import java.math.BigDecimal;
import java.text.MessageFormat;
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

  final Cidade serrana = new Cidade();
  final Cidade serraAzul = new Cidade();
  final Cidade sertaozinho = new Cidade();
  final Cidade saoPaulo = new Cidade();

  public CidadeServiceImplTest() {
    serrana.setId("1");
    serrana.setLongitude(new BigDecimal("-47.5977620963"));
    serrana.setLatitude(new BigDecimal("-21.209477985"));
    serraAzul.setId("2");
    serraAzul.setLongitude(new BigDecimal("-47.5632499203"));
    serraAzul.setLatitude(new BigDecimal("-21.3102876657"));
    sertaozinho.setId("3");
    sertaozinho.setLongitude(new BigDecimal("-47.991148431"));
    sertaozinho.setLatitude(new BigDecimal("-21.137021505"));
    saoPaulo.setId("4");
    saoPaulo.setLongitude(new BigDecimal("-46.5703831821"));
    saoPaulo.setLatitude(new BigDecimal("-23.5673865"));
  }

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
  public void getEstadoCidadeMenorMaior() throws NotFoundServiceException {
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

  @Test
  public void calcularDistancia() {
    CidadeServiceImpl service = new CidadeServiceImpl(null, null, null);

    System.out.println(MessageFormat.format("Serrana -> Sertãozinho = {0}", service.calcularDistancia(serrana, sertaozinho)));
    System.out.println(MessageFormat.format("Serrana -> Serra Azul = {0}", service.calcularDistancia(serrana, serraAzul)));
    System.out.println(MessageFormat.format("Serrana -> São Paulo = {0}", service.calcularDistancia(serrana, saoPaulo)));
    System.out.println(MessageFormat.format("Sertãozinho -> São Paulo = {0}", service.calcularDistancia(sertaozinho, saoPaulo)));
  }

  @Test
  public void maisDistantes() throws NotFoundServiceException {
    final CidadeRepository repository = Mockito.mock(CidadeRepository.class);
    doReturn(Arrays.asList(serrana, serraAzul, sertaozinho, saoPaulo)).when(repository).findAll();
    final CidadeServiceImpl service = new CidadeServiceImpl(repository, null, null);

    final List<Cidade> cidades = service.maisDistantes();
    Assert.assertNotNull(cidades);
    Assert.assertEquals(2, cidades.size());
    Assert.assertTrue(cidades.stream().anyMatch(x -> x.equals(sertaozinho)));
    Assert.assertTrue(cidades.stream().anyMatch(x -> x.equals(saoPaulo)));
  }
}