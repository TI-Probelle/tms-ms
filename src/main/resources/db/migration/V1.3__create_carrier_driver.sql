-- Creates carrier_driver table
CREATE TABLE carrier_driver (
  id BIGINT UNSIGNED NOT NULL AUTO_INCREMENT,
  uuid BINARY(16) NOT NULL DEFAULT (UUID_TO_BIN(UUID())),
  carrier_id BIGINT UNSIGNED NOT NULL,
  name VARCHAR(160) NOT NULL,
  doc_number VARCHAR(20) NULL,
  plate_main VARCHAR(10) NULL,
  plate_trailer VARCHAR(10) NULL,
  active BOOLEAN NOT NULL DEFAULT TRUE,
  created_at DATETIME(3) NOT NULL DEFAULT CURRENT_TIMESTAMP(3),
  updated_at DATETIME(3) NOT NULL DEFAULT CURRENT_TIMESTAMP(3) ON UPDATE CURRENT_TIMESTAMP(3),
  PRIMARY KEY (id),
  UNIQUE KEY uk_carrier_driver__uuid (uuid),
  KEY idx_carrier_driver__carrier (carrier_id),
  CONSTRAINT fk_carrier_driver__carrier FOREIGN KEY (carrier_id) REFERENCES carrier(id)
    ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
