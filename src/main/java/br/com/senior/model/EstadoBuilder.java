package br.com.senior.model;

/**
 * Classe builder para facilitar a criação do objeto tipo estado.
 */
public final class EstadoBuilder {

  private String id;
  private String sigla;

  private EstadoBuilder() {
  }

  public static EstadoBuilder newInstance() {
    return new EstadoBuilder();
  }

  public EstadoBuilder withId(String id) {
    this.id = id;
    return this;
  }

  public EstadoBuilder withSigla(String sigla) {
    this.sigla = sigla;
    return this;
  }

  public Estado build() {
    final Estado estado = new Estado();
    estado.setId(id);
    estado.setSigla(sigla);
    return estado;
  }
}
