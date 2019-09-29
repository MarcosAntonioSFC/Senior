package br.com.senior.model.others;

import java.io.Serializable;

/**
 * Objeto para representar o resultado da questão 2.
 */
public class EstadoCidade implements Serializable {

  /**
   * Nome do estado.
   */
  private String nome;

  /**
   * Quantidade de cidade no estado.
   */
  private long quantidade;

  public EstadoCidade() {
    //Construtor Padrão.
  }

  /**
   * Construtor para resolver a buscar no repositório.
   *
   * @param nome       nome
   * @param quantidade quantidade
   */
  public EstadoCidade(final String nome, final long quantidade) {
    this.nome = nome;
    this.quantidade = quantidade;
  }

  public String getNome() {
    return nome;
  }

  public void setNome(final String nome) {
    this.nome = nome;
  }

  public long getQuantidade() {
    return quantidade;
  }

  public void setQuantidade(final long quantidade) {
    this.quantidade = quantidade;
  }
}
