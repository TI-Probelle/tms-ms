package br.com.probelle.sistemas.probelle_tms.v1.services.exceptions;

public class ConflictException extends RuntimeException {
  public ConflictException(String message) {
    super(message);
  }
}
