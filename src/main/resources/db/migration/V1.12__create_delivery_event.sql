-- Creates delivery_event table
CREATE TABLE delivery_event (
  id BIGINT UNSIGNED NOT NULL AUTO_INCREMENT,
  uuid BINARY(16) NOT NULL DEFAULT (UUID_TO_BIN(UUID())),
  manifest_id BIGINT UNSIGNED NOT NULL,
  invoice_id BIGINT UNSIGNED NULL,
  edi_document_id BIGINT UNSIGNED NOT NULL,
  occurrence_code VARCHAR(20) NOT NULL,
  occurrence_desc VARCHAR(255) NOT NULL,
  occurred_at DATETIME(3) NOT NULL,
  note VARCHAR(255) NULL,
  created_at DATETIME(3) NOT NULL DEFAULT CURRENT_TIMESTAMP(3),
  updated_at DATETIME(3) NOT NULL DEFAULT CURRENT_TIMESTAMP(3) ON UPDATE CURRENT_TIMESTAMP(3),
  PRIMARY KEY (id),
  UNIQUE KEY uk_delivery_event__uuid (uuid),
  KEY idx_delivery_event__manifest_time (manifest_id, occurred_at),
  KEY idx_delivery_event__invoice_time (invoice_id, occurred_at),
  KEY idx_delivery_event__edi_document (edi_document_id),
  CONSTRAINT fk_delivery_event__manifest FOREIGN KEY (manifest_id) REFERENCES manifest(id)
    ON DELETE RESTRICT ON UPDATE RESTRICT,
  CONSTRAINT fk_delivery_event__invoice FOREIGN KEY (invoice_id) REFERENCES invoice(id)
    ON DELETE RESTRICT ON UPDATE RESTRICT,
  CONSTRAINT fk_delivery_event__edi_document FOREIGN KEY (edi_document_id) REFERENCES edi_document(id)
    ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
