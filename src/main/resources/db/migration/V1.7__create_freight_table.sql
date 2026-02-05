-- Creates freight_table table
CREATE TABLE freight_table (
  id BIGINT UNSIGNED NOT NULL AUTO_INCREMENT,
  uuid BINARY(16) NOT NULL DEFAULT (UUID_TO_BIN(UUID())),
  carrier_id BIGINT UNSIGNED NOT NULL,
  name VARCHAR(120) NOT NULL,
  mode ENUM('KG','FIXED','MIXED') NOT NULL,
  valid_from DATE NOT NULL,
  valid_to DATE NOT NULL,
  active BOOLEAN NOT NULL DEFAULT TRUE,
  created_at DATETIME(3) NOT NULL DEFAULT CURRENT_TIMESTAMP(3),
  updated_at DATETIME(3) NOT NULL DEFAULT CURRENT_TIMESTAMP(3) ON UPDATE CURRENT_TIMESTAMP(3),
  PRIMARY KEY (id),
  UNIQUE KEY uk_freight_table__uuid (uuid),
  KEY idx_freight_table__carrier_active_valid (carrier_id, active, valid_from, valid_to),
  CONSTRAINT fk_freight_table__carrier FOREIGN KEY (carrier_id) REFERENCES carrier(id)
    ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
