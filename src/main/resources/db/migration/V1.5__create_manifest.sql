-- Creates manifest table
CREATE TABLE manifest (
  id BIGINT UNSIGNED NOT NULL AUTO_INCREMENT,
  uuid BINARY(16) NOT NULL DEFAULT (UUID_TO_BIN(UUID())),
  manifest_no VARCHAR(40) NOT NULL,
  customer_id BIGINT UNSIGNED NOT NULL,
  carrier_id BIGINT UNSIGNED NOT NULL,
  origin_region_id BIGINT UNSIGNED NOT NULL,
  dest_region_id BIGINT UNSIGNED NOT NULL,
  status ENUM('OPEN','LOCKED','IN_TRANSIT','DELIVERED','PENDING','CANCELLED') NOT NULL DEFAULT 'OPEN',
  locked_at DATETIME(3) NULL,
  total_weight_kg DECIMAL(15,3) NOT NULL DEFAULT 0,
  total_value DECIMAL(15,2) NOT NULL DEFAULT 0,
  created_at DATETIME(3) NOT NULL DEFAULT CURRENT_TIMESTAMP(3),
  updated_at DATETIME(3) NOT NULL DEFAULT CURRENT_TIMESTAMP(3) ON UPDATE CURRENT_TIMESTAMP(3),
  PRIMARY KEY (id),
  UNIQUE KEY uk_manifest__uuid (uuid),
  UNIQUE KEY uk_manifest__manifest_no (manifest_no),
  KEY idx_manifest__customer_carrier_status (customer_id, carrier_id, status, created_at),
  KEY idx_manifest__carrier (carrier_id),
  KEY idx_manifest__origin_region (origin_region_id),
  KEY idx_manifest__dest_region (dest_region_id),
  CONSTRAINT fk_manifest__customer FOREIGN KEY (customer_id) REFERENCES customer(id)
    ON DELETE RESTRICT ON UPDATE RESTRICT,
  CONSTRAINT fk_manifest__carrier FOREIGN KEY (carrier_id) REFERENCES carrier(id)
    ON DELETE RESTRICT ON UPDATE RESTRICT,
  CONSTRAINT fk_manifest__origin_region FOREIGN KEY (origin_region_id) REFERENCES region(id)
    ON DELETE RESTRICT ON UPDATE RESTRICT,
  CONSTRAINT fk_manifest__dest_region FOREIGN KEY (dest_region_id) REFERENCES region(id)
    ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
