-- Creates edi_ref table
CREATE TABLE edi_ref (
  id BIGINT UNSIGNED NOT NULL AUTO_INCREMENT,
  uuid BINARY(16) NOT NULL DEFAULT (UUID_TO_BIN(UUID())),
  edi_document_id BIGINT UNSIGNED NOT NULL,
  ref_type ENUM('MANIFEST','INVOICE','CTE','OTHER') NOT NULL,
  ref_id BIGINT UNSIGNED NOT NULL,
  created_at DATETIME(3) NOT NULL DEFAULT CURRENT_TIMESTAMP(3),
  updated_at DATETIME(3) NOT NULL DEFAULT CURRENT_TIMESTAMP(3) ON UPDATE CURRENT_TIMESTAMP(3),
  PRIMARY KEY (id),
  UNIQUE KEY uk_edi_ref__uuid (uuid),
  KEY idx_edi_ref__document_type (edi_document_id, ref_type),
  CONSTRAINT fk_edi_ref__edi_document FOREIGN KEY (edi_document_id) REFERENCES edi_document(id)
    ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
