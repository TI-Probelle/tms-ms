-- Creates edi_document table
CREATE TABLE edi_document (
  id BIGINT UNSIGNED NOT NULL AUTO_INCREMENT,
  uuid BINARY(16) NOT NULL DEFAULT (UUID_TO_BIN(UUID())),
  carrier_id BIGINT UNSIGNED NOT NULL,
  edi_type ENUM('NOTFIS','OCOREN','DOCCOB','DOCCOP','OUTROS') NOT NULL,
  version VARCHAR(20) NULL,
  direction ENUM('OUTBOUND','INBOUND') NOT NULL,
  file_name VARCHAR(255) NOT NULL,
  received_at DATETIME(3) NULL,
  generated_at DATETIME(3) NULL,
  status ENUM('NEW','PARSED','ERROR','APPLIED') NOT NULL DEFAULT 'NEW',
  raw_content LONGTEXT NULL,
  created_at DATETIME(3) NOT NULL DEFAULT CURRENT_TIMESTAMP(3),
  updated_at DATETIME(3) NOT NULL DEFAULT CURRENT_TIMESTAMP(3) ON UPDATE CURRENT_TIMESTAMP(3),
  PRIMARY KEY (id),
  UNIQUE KEY uk_edi_document__uuid (uuid),
  KEY idx_edi_document__carrier_type_dir_status (carrier_id, edi_type, direction, status),
  KEY idx_edi_document__received_generated (received_at, generated_at),
  CONSTRAINT fk_edi_document__carrier FOREIGN KEY (carrier_id) REFERENCES carrier(id)
    ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
