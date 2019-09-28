package br.com.senior.controller.services;

import br.com.senior.controller.abstracts.AbstractService;
import br.com.senior.controller.abstracts.ServiceException;
import br.com.senior.controller.repository.CidadeRepository;
import br.com.senior.model.Cidade;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

/**
 * Serviço responsável pelas regras de negócio da cidade.
 */
@Service
public class CidadeServiceImpl extends AbstractService<Cidade> implements CidadeService {

  private static final String ERRO_NA_LEITURO_ARQUIVO = "Houve problemas na leitura do arquivo";

  /**
   * Construtor da classe.
   *
   * @param repository CDI.
   */
  @Autowired
  public CidadeServiceImpl(final CidadeRepository repository) {
    super(repository);
  }

  /**
   * Método para fazer o upload do arquivo.
   *
   * @param file arquivo de upload.
   */
  @Override
  public void upload(final MultipartFile file) throws ServiceException {
    try {
      byte[] bytes = file.getBytes();
      Path path = Paths.get(/*UPLOADED_FOLDER + */file.getOriginalFilename());
      Files.write(path, bytes);

    } catch (IOException e) {
      throw new ServiceException(ERRO_NA_LEITURO_ARQUIVO);
    }


  }
}
