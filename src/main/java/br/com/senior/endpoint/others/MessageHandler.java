package br.com.senior.endpoint.others;

import java.io.Serializable;

/**
 * Classe para responde no handler.
 */
public class MessageHandler implements Serializable {

  private String message;

  private int status;

  public MessageHandler(final String message, final int status) {
    this.message = message;
    this.status = status;
  }

  public MessageHandler() {
  }

  public String getMessage() {
    return message;
  }

  public void setMessage(final String message) {
    this.message = message;
  }

  public int getStatus() {
    return status;
  }

  public void setStatus(final int status) {
    this.status = status;
  }
}
