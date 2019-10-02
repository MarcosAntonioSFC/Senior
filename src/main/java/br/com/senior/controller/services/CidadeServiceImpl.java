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
import java.math.BigDecimal;
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
  private static final double RAIO_TERRA = 6371.0;
  private static final BigDecimal DOIS = new BigDecimal("2");
  private static final String REGISTROS = "Não existe nenhum registro";
  private final EstadoService estadoService;

  private final CustomCidadeRepository customCidadeRepository;

  /**
   * Construtor da classe.
   *
   * @param repository             CDI.
   * @param customCidadeRepository CDI.
   * @param estadoService          CDI do serviço estado.
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
  public List<EstadoCidade> getEstadoCidadeMenorMaior() throws NotFoundServiceException {
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
  public List<EstadoCidade> getCidadeEstados() throws NotFoundServiceException {
    final List<EstadoCidade> estadoCidades = getRepository().findEstadoCidades();
    if (estadoCidades.isEmpty()) {
      throw new NotFoundServiceException(REGISTROS);
    }

    return estadoCidades;
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

  /**
   * Varre a lista de cidades atuais para encontrar as as mais distantes.
   *
   * @return as 2 cidades mais distantes.
   */
  @Override
  public List<Cidade> maisDistantes() throws NotFoundServiceException {
    final List<Cidade> all = getRepository().findAll();
    if (all.isEmpty()) {
      throw new NotFoundServiceException(REGISTROS);
    }
    double ultimaDistancia = 0;
    Cidade cidadeDistante1 = null;
    Cidade cidadeDistante2 = null;

    for (final Cidade cidade1 : all) {
      for (final Cidade cidade2 : all) {
        final double distancia = calcularDistancia(cidade1, cidade2);
        if (distancia > ultimaDistancia) {
          ultimaDistancia = distancia;
          cidadeDistante1 = cidade1;
          cidadeDistante2 = cidade2;
        }
      }
    }

    return Arrays.asList(cidadeDistante1, cidadeDistante2);
  }

  /**
   * Método que cálcula a distancia entre as duas cidades.
   *
   * @param cidade  cidade um.
   * @param cidade2 cidade dois.
   * @return distancia entre as cidades.
   */
  protected double calcularDistancia(final Cidade cidade, final Cidade cidade2) {
    //a + b + 90 = 180º -> teoria dos triangulos (soma interna dos angulos)
    //a = 90 - b
    //b = 90 - a
    //tan a = DLA / DLO
    //sen a = DLA / distancia
    //cos a = DLO / distancia
    final double long1 = cidade.getLongitude().doubleValue();
    final double lat1 = cidade.getLatitude().doubleValue();
    final double long2 = cidade2.getLongitude().doubleValue();
    final double lat2 = cidade2.getLatitude().doubleValue();

    //Diferença longitude
    final double dlo = long2 - long1;
    //Diferença latitude
    final double dla = lat2 - lat1;

    final double sinDla = Math.pow(Math.sin(rad(dla / 2)), 2);
    final double sinDlo = Math.pow(Math.sin(rad(dlo / 2)), 2);

    final double cosLat1 = Math.cos(rad(lat1));
    final double cosLat2 = Math.cos(rad(lat2));

    final double dist = (sinDla + sinDlo) * (cosLat1 + cosLat2);

    return RAIO_TERRA * (2 * Math.atan2(Math.sqrt(dist), Math.sqrt(1 - dist)));
  }

  private double rad(final double dlHalf) {
    return (dlHalf * Math.PI / 180.0);
  }

}
