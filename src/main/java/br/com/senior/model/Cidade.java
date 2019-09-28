package br.com.senior.model;

import br.com.senior.model.others.AbstractModel;
import br.com.senior.model.others.LengthConstants;
import br.com.senior.model.others.TypeColumnEntity;

import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.validation.constraints.NotNull;

/**
 * Classe para representação das cidades.
 */
@Entity(name = Cidade.TABLE_NAME)
public class Cidade extends AbstractModel {

  public static final String TABLE_NAME = "CAD_CIDADE";
  public static final String ID_COLNAME = "CODIGO_IBGE";
  private static final String NOME_COLNAME = "NOME";
  private static final String CAPITAL_COLNAME = "CAPITAL";
  private static final String LATITUDE_COLNAME = "LATITUDE";
  private static final String LONGITUDE_COLNAME = "LONGITUDE";
  private static final String NO_ACCENTS_COLNAME = "NO_ACCENTS";
  private static final String NOME_ALTERNATIVO_COLNAME = "NOME_ALTERNATIVO";
  private static final String MICRO_REGIAO_COLNAME = "MICRO_REGIAO";
  private static final String MESO_REGIAO_COLNAME = "MESO_REGIAO";

  /**
   * Identificador do registro, será o própio código da cidade.
   */
  @Id
  @Column(name = ID_COLNAME)
  private String id;

  /**
   * Relação com o estado.
   */
  @NotNull
  @ManyToOne(targetEntity = Estado.class, fetch = FetchType.LAZY, optional = false)
  @JoinColumn(name = Estado.ID_COLNAME)
  private Estado estado;

  /**
   * Nome da cidade.
   */
  @NotNull
  @Column(name = NOME_COLNAME, length = LengthConstants.CIDADE_NOME, nullable = false)
  private String nome;

  /**
   * Indica se o registro é capital.
   */
  @Column(name = CAPITAL_COLNAME)
  private Boolean capital;

  /**
   * Latitude da cidade
   */
  @Column(name = LATITUDE_COLNAME, columnDefinition = TypeColumnEntity.DOUBLE_DECIMAL_TYPE)
  private BigDecimal latitude;

  /**
   * Longitute da cidade
   */
  @Column(name = LONGITUDE_COLNAME, columnDefinition = TypeColumnEntity.DOUBLE_DECIMAL_TYPE)
  private BigDecimal longitude;

  /**
   * Sem acentos
   */
  @Column(name = NO_ACCENTS_COLNAME, length = LengthConstants.CIDADE_NOME_NOACCENTS, nullable = false)
  private String noAccents;

  /**
   * Armazena o nome alternativo.
   */
  @Column(name = NOME_ALTERNATIVO_COLNAME, length = LengthConstants.CIDADE_NOME, nullable = false)
  private String nomeAlternativo;

  /**
   * Micro região
   */
  @Column(name = MICRO_REGIAO_COLNAME, length = LengthConstants.MICRO_REGIAO, nullable = false)
  private String microRegiao;

  /**
   * Meso região
   */
  @Column(name = MESO_REGIAO_COLNAME, length = LengthConstants.MESO_REGIAO, nullable = false)
  private String mesoRegiao;

  public Cidade() {
    //Construtor Padrão
  }

  public String getId() {
    return id;
  }

  public void setId(final String id) {
    this.id = id;
  }

  public Estado getEstado() {
    return estado;
  }

  public void setEstado(final Estado estado) {
    this.estado = estado;
  }

  public String getNome() {
    return nome;
  }

  public void setNome(final String nome) {
    this.nome = nome;
  }

  public Boolean getCapital() {
    return capital;
  }

  public void setCapital(final Boolean capital) {
    this.capital = capital;
  }

  public BigDecimal getLatitude() {
    return latitude;
  }

  public void setLatitude(final BigDecimal latitude) {
    this.latitude = latitude;
  }

  public BigDecimal getLongitude() {
    return longitude;
  }

  public void setLongitude(final BigDecimal longitude) {
    this.longitude = longitude;
  }

  public String getNoAccents() {
    return noAccents;
  }

  public void setNoAccents(final String noAccents) {
    this.noAccents = noAccents;
  }

  public String getNomeAlternativo() {
    return nomeAlternativo;
  }

  public void setNomeAlternativo(final String nomeAlternativo) {
    this.nomeAlternativo = nomeAlternativo;
  }

  public String getMicroRegiao() {
    return microRegiao;
  }

  public void setMicroRegiao(final String microRegiao) {
    this.microRegiao = microRegiao;
  }

  public String getMesoRegiao() {
    return mesoRegiao;
  }

  public void setMesoRegiao(final String mesoRegiao) {
    this.mesoRegiao = mesoRegiao;
  }
}
