package br.com.senior.controller.services;

import br.com.senior.controller.abstracts.AbstractService;
import br.com.senior.controller.abstracts.NotFoundServiceException;
import br.com.senior.controller.abstracts.ServiceException;
import br.com.senior.controller.others.CsvUtils;
import br.com.senior.controller.repository.CidadeRepository;
import br.com.senior.controller.repository.CustomCidadeRepository;
import br.com.senior.model.Cidade;
import br.com.senior.model.Estado;
import br.com.senior.model.others.EstadoCidade;
import com.google.common.base.Strings;

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
  private static final String ESTADO_NOT_FOUND = "Estado não informado";
  private static final String CAPITAL_JA_EXISTE = "Já existe uma capital para este estado";
  private final EstadoService estadoService;

  private final CustomCidadeRepository customCidadeRepository;

  /**
   * Construtor da classe.
   *
   * @param repository    CDI.
   * @param customCidadeRepository CDI.
   * @param estadoService CDI do serviço estado.
   * @see EstadoService
   */
  @Autowired
  public CidadeServiceImpl(final CidadeRepository repository, final CustomCidadeRepository customCidadeRepository,
                           final EstadoService estadoService) {
    super(repository);
    this.customCidadeRepository = customCidadeRepository;
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

  /**
   * Método com a validação se o estado vinculado a cidade já existe.
   *
   * @param entity entidade.
   */
  @Override
  public void beforeAdd(final Cidade entity) throws ServiceException {
    super.beforeAdd(entity);
    Estado estado;
    try {
      estado = estadoService.getById(entity.getId());
    } catch (NotFoundServiceException e) {
      estado = estadoService.save(entity.getEstado());
    }

    if (entity.getCapital()) {
      final Cidade capital = getCapitalByEstado(estado.getId());
      if (null != capital) {
        throw new ServiceException(CAPITAL_JA_EXISTE);
      }
    }

    entity.setEstado(estado);
  }

  /**
   * Busca a capital por estado.
   *
   * @return cidade capital por estado.
   */
  private Cidade getCapitalByEstado(final String uf) {
    return getRepository().findCapitalByEstado(uf);
  }

  /**
   * Efetua a busca das cidades de um estado.
   *
   * @param uf estado para consultar as cidades.
   * @return lista de cidades respetiva ao estado.
   */
  @Override
  public List<String> findByEstado(final String uf) throws ServiceException {
    if (Strings.isNullOrEmpty(uf)) {
      throw new ServiceException(ESTADO_NOT_FOUND);
    }
    return getRepository().findByEstado(uf);
  }

  /**
   * #9 - Permite a busca de um registro baseando-se na coluna.
   *
   * @param column coluna
   * @param valor  valor
   * @return objetos que obedecem a condição.
   */
  @Override
  public List<Cidade> findByColumn(final String column, final String valor) throws ServiceException {
    return customCidadeRepository.findByColumnValor(column, valor);
  }

  /**
   * #10 - Contagem da coluna
   *
   * @param column coluna para contagem dos registros
   * @return quantidade distintos de registro
   */
  @Override
  public Long countByColumn(final String column) throws ServiceException {
    return customCidadeRepository.countByColumn(column);
  }

  /**
   * Conta o total de registros no banco de dados.
   *
   * @return quantidade.
   */
  @Override
  public long countAll() {
    return getRepository().count();
  }
}
