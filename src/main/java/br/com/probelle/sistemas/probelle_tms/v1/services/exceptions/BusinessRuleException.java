package br.com.probelle.sistemas.probelle_tms.v1.services.exceptions;

public class BusinessRuleException extends ConflictException {
  public BusinessRuleException(String message) {
    super(message);
  }
}
