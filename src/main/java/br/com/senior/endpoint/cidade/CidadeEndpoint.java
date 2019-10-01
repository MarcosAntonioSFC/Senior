package br.com.senior.endpoint.cidade;

import br.com.senior.controller.abstracts.ServiceException;
import br.com.senior.controller.services.CidadeService;
import br.com.senior.endpoint.abstracts.ResponseWrapper;
import br.com.senior.endpoint.cidade.responses.CidadesResponseWrapper;
import br.com.senior.endpoint.cidade.responses.EstadoCidadesResponseWrapper;
import br.com.senior.model.Cidade;

import java.text.MessageFormat;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

/**
 * Endpoint para acesso às funcionalidades.
 */
@RestController
@RequestMapping("/cidade/")
public class CidadeEndpoint extends AbstractEndpoint<Cidade, CidadeService> {

  private static final Logger logger = LoggerFactory.getLogger(CidadeEndpoint.class);

  /**
   * Construtor do endpoint.
   *
   * @param service CDI.
   */
  @Autowired
  public CidadeEndpoint(CidadeService service) {
    super(service);
  }

  /**
   * #1 - Método para ser utilizado na leitura e criação dos dados.
   *
   * @return mensagem de sucesso ou não.
   * @see ResponseWrapper
   */
  @PostMapping(
      path = "/upload/"
  )
  public CidadesResponseWrapper readCsv(@RequestParam("file") MultipartFile file) {
    try {
      logger.info("Recebendo aquivo");
      final List<Cidade> cidades = getService().upload(file);
      return new CidadesResponseWrapper(MessageFormat.format("Importado {0} cidades com Sucesso!", cidades.size()), HttpStatus.OK.value());
    } catch (ServiceException e) {
      return new CidadesResponseWrapper(e.getMessage(), HttpStatus.CONFLICT.value());
    }
  }

  /**
   * #2 - Retorna apenas as cidades que são capitais ordenando pelo nome.
   *
   * @return cidades capitais.
   */
  @GetMapping(
      path = "/capitais/",
      produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE}
  )
  public CidadesResponseWrapper capitais() {
    try {
      logger.info("Consultando capitais");
      return new CidadesResponseWrapper(getService().getCapitais(), HttpStatus.OK.value());
    } catch (Throwable e) {
      return new CidadesResponseWrapper(e.getMessage(), HttpStatus.CONFLICT.value());
    }
  }

  /**
   * #3 - Estado maior e menor considerando a quantidade de cidades.
   *
   * @return Estados com maior e menos quantidade.
   */
  @GetMapping(
      path = "/tamanho/estados/cidades/",
      produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE}
  )
  public EstadoCidadesResponseWrapper estadoCidadeMenorMaior() {
    try {
      logger.info("Consultando maior e menor estado");
      return new EstadoCidadesResponseWrapper(getService().getEstadoCidadeMenorMaior(), HttpStatus.OK.value());
    } catch (Throwable e) {
      return new EstadoCidadesResponseWrapper(e.getMessage(), HttpStatus.CONFLICT.value());
    }
  }

  /**
   * #4 - Estado, quantidade de cidades.
   *
   * @return Todos os estado com as respectivas quantidades de municipios.
   */
  @GetMapping(
      path = "/cidades/estados/",
      produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE}
  )
  public EstadoCidadesResponseWrapper byEstado() {
    try {
      logger.info("Consultando Estado e Cidades");
      return new EstadoCidadesResponseWrapper(getService().getCidadeEstados(), HttpStatus.OK.value());
    } catch (Throwable e) {
      return new EstadoCidadesResponseWrapper(e.getMessage(), HttpStatus.CONFLICT.value());
    }
  }

  /**
   * #6 - Cidades por estado.
   *
   * @return Todos as cidades do respetivo estado.
   */
  @GetMapping(
      path = "/by/estado/",
      produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE}
  )
  public ResponseWrapper<List<String>> byEstado(@RequestParam("uf") final String uf) {
    try {
      logger.info("Consultando Cidades por Estado");
      return new ResponseWrapper<>(getService().findByEstado(uf), HttpStatus.OK.value());
    } catch (Throwable e) {
      return new ResponseWrapper<>(e.getMessage(), HttpStatus.CONFLICT.value());
    }
  }
}
