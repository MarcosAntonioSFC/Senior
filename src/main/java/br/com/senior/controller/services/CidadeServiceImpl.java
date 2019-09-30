package br.com.senior.controller.services;

import br.com.senior.controller.abstracts.AbstractService;
import br.com.senior.controller.abstracts.ServiceException;
import br.com.senior.controller.others.CsvUtils;
import br.com.senior.controller.repository.CidadeRepository;
import br.com.senior.model.Cidade;
import br.com.senior.model.Estado;
import br.com.senior.model.others.EstadoCidade;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

/**
 * Serviço responsável pelas regras de negócio da cidade.
 */
@Service
public class CidadeServiceImpl extends AbstractService<Cidade, CidadeRepository> implements CidadeService {

  private static final String ERRO_NA_LEITURO_ARQUIVO = "Houve problemas na leitura do arquivo";
  private final EstadoService estadoService;

  /**
   * Construtor da classe.
   *
   * @param repository    CDI.
   * @param estadoService CDI do serviço estado.
   * @see EstadoService
   */
  @Autowired
  public CidadeServiceImpl(final CidadeRepository repository, final EstadoService estadoService) {
    super(repository);
    this.estadoService = estadoService;
  }

  /**
   * Método para fazer o upload do arquivo.
   *
   * @param file arquivo de upload.
   * @return cidades inseridas
   */
  @Override
  public List<Cidade> upload(final MultipartFile file) throws ServiceException {
    try {
      final ArrayList<Cidade> cidades = CsvUtils.read(file.getInputStream());
      final List<Estado> ufs = cidades.stream().map(Cidade::getEstado).distinct().collect(Collectors.toList());
      estadoService.save(ufs);
      return save(cidades);
    } catch (IOException e) {
      throw new ServiceException(ERRO_NA_LEITURO_ARQUIVO);
    }
  }

  /**
   * Retorna as cidades capitais ordenadas pelo nome.
   *
   * @return capitais
   */
  @Override
  public List<Cidade> getCapitais() {
    return getRepository().findCidadeByCapitalOrderByNome(true);
  }

  /**
   * Método para fazer a consulta da questão dois.
   *
   * @return estdado maior e menor de acordo com a quantidade de cidade.
   */
  @Override
  public List<EstadoCidade> getEstadoCidadeMenorMaior() {
    final List<EstadoCidade> estadoCidades = getCidadeEstados();
    return Arrays.asList(
        estadoCidades.stream().min(Comparator.comparing(EstadoCidade::getQuantidade)).get(),
        estadoCidades.stream().max(Comparator.comparing(EstadoCidade::getQuantidade)).get()
    );
  }

  /**
   * Método para a consulta de estado com suas respectivas quantidades de municipios.
   *
   * @return Lista dos estados com suas respectivas quantidades de municipios.
   */
  @Override
  public List<EstadoCidade> getCidadeEstados() {
    return getRepository().findEstadoCidades();
  }
}
