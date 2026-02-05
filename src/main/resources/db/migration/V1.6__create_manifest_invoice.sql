-- Creates manifest_invoice table
CREATE TABLE manifest_invoice (
  id BIGINT UNSIGNED NOT NULL AUTO_INCREMENT,
  uuid BINARY(16) NOT NULL DEFAULT (UUID_TO_BIN(UUID())),
  manifest_id BIGINT UNSIGNED NOT NULL,
  invoice_id BIGINT UNSIGNED NOT NULL,
  seq INT NOT NULL,
  weight_kg DECIMAL(15,3) NOT NULL DEFAULT 0,
  value DECIMAL(15,2) NOT NULL DEFAULT 0,
  linked_at DATETIME(3) NOT NULL DEFAULT CURRENT_TIMESTAMP(3),
  created_at DATETIME(3) NOT NULL DEFAULT CURRENT_TIMESTAMP(3),
  updated_at DATETIME(3) NOT NULL DEFAULT CURRENT_TIMESTAMP(3) ON UPDATE CURRENT_TIMESTAMP(3),
  PRIMARY KEY (id),
  UNIQUE KEY uk_manifest_invoice__uuid (uuid),
  UNIQUE KEY uk_manifest_invoice__manifest_invoice (manifest_id, invoice_id),
  KEY idx_manifest_invoice__manifest_seq (manifest_id, seq),
  KEY idx_manifest_invoice__invoice (invoice_id),
  CONSTRAINT fk_manifest_invoice__manifest FOREIGN KEY (manifest_id) REFERENCES manifest(id)
    ON DELETE RESTRICT ON UPDATE RESTRICT,
  CONSTRAINT fk_manifest_invoice__invoice FOREIGN KEY (invoice_id) REFERENCES invoice(id)
    ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
