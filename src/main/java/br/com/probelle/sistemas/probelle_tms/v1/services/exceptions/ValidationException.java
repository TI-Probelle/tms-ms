package br.com.probelle.sistemas.probelle_tms.v1.services.exceptions;

public class ValidationException extends RuntimeException {
  public ValidationException(String message) {
    super(message);
  }
}
