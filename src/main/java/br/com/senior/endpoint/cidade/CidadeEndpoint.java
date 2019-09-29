package br.com.senior.endpoint.cidade;

import br.com.senior.controller.abstracts.ServiceException;
import br.com.senior.controller.services.CidadeService;
import br.com.senior.endpoint.abstracts.AbstractResponseWrapper;
import br.com.senior.endpoint.abstracts.MessageResponseWrapper;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
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
  public MessageResponseWrapper readCsv(@RequestParam("file") MultipartFile file) {
    try {
      logger.info("Recebendo aquivo");
      service.upload(file);
      return new MessageResponseWrapper("Recebido", 200);
    } catch (ServiceException e) {
      return new MessageResponseWrapper(e.getMessage(), 409);
    }
  }

}
