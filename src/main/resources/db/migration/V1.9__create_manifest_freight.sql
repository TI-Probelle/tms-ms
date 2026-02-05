-- Creates manifest_freight table
CREATE TABLE manifest_freight (
  id BIGINT UNSIGNED NOT NULL AUTO_INCREMENT,
  uuid BINARY(16) NOT NULL DEFAULT (UUID_TO_BIN(UUID())),
  manifest_id BIGINT UNSIGNED NOT NULL,
  pricing_source ENUM('TABLE','NEGOTIATED','MANUAL') NOT NULL,
  pricing_mode ENUM('KG','FIXED','KM','DAILY','MIXED') NOT NULL,
  freight_table_id BIGINT UNSIGNED NULL,
  base_weight_kg DECIMAL(15,3) NOT NULL DEFAULT 0,
  base_value DECIMAL(15,2) NOT NULL DEFAULT 0,
  freight_total DECIMAL(15,2) NOT NULL DEFAULT 0,
  breakdown_json JSON NULL,
  negotiated_by_user_id BIGINT UNSIGNED NULL,
  negotiated_at DATETIME(3) NULL,
  notes VARCHAR(500) NULL,
  created_at DATETIME(3) NOT NULL DEFAULT CURRENT_TIMESTAMP(3),
  updated_at DATETIME(3) NOT NULL DEFAULT CURRENT_TIMESTAMP(3) ON UPDATE CURRENT_TIMESTAMP(3),
  PRIMARY KEY (id),
  UNIQUE KEY uk_manifest_freight__uuid (uuid),
  UNIQUE KEY uk_manifest_freight__manifest (manifest_id),
  KEY idx_manifest_freight__freight_table (freight_table_id),
  CONSTRAINT fk_manifest_freight__manifest FOREIGN KEY (manifest_id) REFERENCES manifest(id)
    ON DELETE RESTRICT ON UPDATE RESTRICT,
  CONSTRAINT fk_manifest_freight__freight_table FOREIGN KEY (freight_table_id) REFERENCES freight_table(id)
    ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
