package br.com.senior.endpoint.cidade;

import br.com.senior.endpoint.abstracts.AbstractResponseWrapper;
import br.com.senior.endpoint.abstracts.MessageResponseWrapper;

import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/cidade/")
public class CidadeEndpoint {


  /**
   * Método para ser utilizado na leitura e criação dos dados.
   *
   * @return mensagem de sucesso ou não.
   * @see AbstractResponseWrapper
   */
  @PostMapping(
      path = "/many/",
      consumes = {MediaType.MULTIPART_FORM_DATA_VALUE}
  )
  public MessageResponseWrapper readCsv(@RequestParam MultipartFile file) {
    return new MessageResponseWrapper("Recebido", 200);
  }

}
