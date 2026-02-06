package br.com.probelle.sistemas.probelle_tms.v1.services.edi;

import java.time.LocalDateTime;

public record OcorenEvent(
    String manifestNo, String code, String description, LocalDateTime occurredAt) {}
