package br.com.senior.endpoint.cidade;

import br.com.senior.controller.abstracts.ServiceException;
import br.com.senior.controller.services.CidadeService;
import br.com.senior.endpoint.abstracts.AbstractResponseWrapper;
import br.com.senior.endpoint.cidade.responses.CidadesResponseWrapper;
import br.com.senior.endpoint.cidade.responses.EstadoCidadesResponseWrapper;
import br.com.senior.model.Cidade;

import java.text.MessageFormat;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
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
public class CidadeEndpoint {

  private static final Logger logger = LoggerFactory.getLogger(CidadeEndpoint.class);

  /**
   * Instancia do serviço.
   */
  private final CidadeService service;

  /**
   * Construtor do endpoint.
   *
   * @param service CDI.
   */
  @Autowired
  public CidadeEndpoint(CidadeService service) {
    this.service = service;
  }

  /**
   * Método para ser utilizado na leitura e criação dos dados.
   *
   * @return mensagem de sucesso ou não.
   * @see AbstractResponseWrapper
   */
  @PostMapping(
      path = "/upload/"
  )
  public CidadesResponseWrapper readCsv(@RequestParam("file") MultipartFile file) {
    try {
      logger.info("Recebendo aquivo");
      final List<Cidade> cidades = service.upload(file);
      return new CidadesResponseWrapper(MessageFormat.format("Importado {0} cidades com Sucesso!", cidades.size()), 200);
    } catch (ServiceException e) {
      return new CidadesResponseWrapper(e.getMessage(), 409);
    }
  }

  /**
   * #1 - Retorna apenas as cidades que são capitais ordenando pelo nome.
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
      return new CidadesResponseWrapper(service.getCapitais(), 200);
    } catch (Throwable e) {
      return new CidadesResponseWrapper(e.getMessage(), 409);
    }
  }

  /**
   * #2 - Estado, quantidade de cidades.
   *
   * @return Estados com maior e menos quantidade.
   */
  @GetMapping(
      path = "/estados/cidades/",
      produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE}
  )
  public EstadoCidadesResponseWrapper estadoCidades() {
    try {
      logger.info("Consultando capitais");
      return new EstadoCidadesResponseWrapper(service.getEstadoCidades(), 200);
    } catch (Throwable e) {
      return new EstadoCidadesResponseWrapper(e.getMessage(), 409);
    }
  }


}
