package br.com.senior.model;

import br.com.senior.model.others.AbstractModel;
import br.com.senior.model.others.LengthConstants;
import com.google.common.base.MoreObjects;
import com.google.common.base.Objects;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

/**
 * Classe para representar os estados do arquivo CSV
 */
@Entity(name = Estado.TABLE_NAME)
public class Estado extends AbstractModel {

  public static final String TABLE_NAME = "CAD_ESTADO";
  public static final String ID_COLNAME = "CAD_ESTADO_ID";
  private static final String SIGLA_NAME = "SIGLA";

  /**
   * Identificador do registro, será o mesmo que a sigla
   */
  @Id
  @Column(name = ID_COLNAME, length = LengthConstants.UF_ID)
  private String id;

  /**
   * Sigla da UF.
   */
  @Column(name = SIGLA_NAME, length = LengthConstants.UF_SIGLA)
  private String sigla;

  public Estado() {
  }

  @Override
  public String getId() {
    return id;
  }

  @Override
  public void setId(final String id) {
    this.id = id;
  }

  public String getSigla() {
    return sigla;
  }

  public void setSigla(final String sigla) {
    this.sigla = sigla;
  }

  @Override
  public boolean equals(final Object o) {
    if (this == o) {
      return true;
    }
    if (!(o instanceof Estado)) {
      return false;
    }
    final Estado estado = (Estado) o;
    return Objects.equal(id, estado.id);
  }

  @Override
  public int hashCode() {
    return Objects.hashCode(id);
  }

  @Override
  public String toString() {
    return MoreObjects.toStringHelper(this)
        .add("id", id)
        .toString();
  }
}
