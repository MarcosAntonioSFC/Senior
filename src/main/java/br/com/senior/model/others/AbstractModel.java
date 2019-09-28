package br.com.senior.model.others;

import java.io.Serializable;

/**
 * Classe abstrata com a implementação da serialização.
 */
public abstract class AbstractModel implements Serializable {

  /**
   * Retorna a chave primária do models.
   *
   * @return PK do model
   */
  public abstract String getId();

  /**
   * Define a chave primária do models.
   *
   * @param id Cahve primária
   */
  public abstract void setId(final String id);

}
