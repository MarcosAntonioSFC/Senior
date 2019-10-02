package br.com.senior.endpoint.others;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

/**
 * Classe para tratar qualquer exceção inesperada.
 */
@ControllerAdvice
public class RestHandler {

  private static final String ERRO = "Ocorreu um erro não esperado";

  @ExceptionHandler({Throwable.class, RuntimeException.class})
  public ResponseEntity<MessageHandler> unexpectedException() {
    return new ResponseEntity(new MessageHandler(ERRO, 409), HttpStatus.OK);
  }

}
