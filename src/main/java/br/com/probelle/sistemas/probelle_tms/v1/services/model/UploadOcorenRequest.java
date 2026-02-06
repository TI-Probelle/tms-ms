package br.com.probelle.sistemas.probelle_tms.v1.services.model;

import java.util.UUID;

public record UploadOcorenRequest(
    UUID carrierUuid, String layoutVersion, String fileName, String rawContent) {}
