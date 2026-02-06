package br.com.probelle.sistemas.probelle_tms.v1.services.model;

import java.util.List;

public record AddInvoicesRequest(List<AddInvoiceItem> invoices) {}
