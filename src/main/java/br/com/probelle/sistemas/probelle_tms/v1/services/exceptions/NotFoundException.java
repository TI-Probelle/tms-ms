package br.com.probelle.sistemas.probelle_tms.v1.services.exceptions;

public class NotFoundException extends RuntimeException {
  public NotFoundException(String message) {
    super(message);
  }
}
