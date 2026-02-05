-- Creates invoice table
CREATE TABLE invoice (
  id BIGINT UNSIGNED NOT NULL AUTO_INCREMENT,
  uuid BINARY(16) NOT NULL DEFAULT (UUID_TO_BIN(UUID())),
  nf_key CHAR(44) NOT NULL,
  nf_number VARCHAR(20) NOT NULL,
  series VARCHAR(10) NOT NULL,
  model VARCHAR(5) NOT NULL,
  issue_date DATE NOT NULL,
  customer_id BIGINT UNSIGNED NOT NULL,
  origin_region_id BIGINT UNSIGNED NOT NULL,
  dest_region_id BIGINT UNSIGNED NOT NULL,
  gross_value DECIMAL(15,2) NOT NULL DEFAULT 0,
  net_value DECIMAL(15,2) NOT NULL DEFAULT 0,
  weight_kg DECIMAL(15,3) NOT NULL DEFAULT 0,
  volume_qty INT NOT NULL DEFAULT 0,
  status ENUM('DRAFT','READY','CANCELLED') NOT NULL DEFAULT 'DRAFT',
  created_at DATETIME(3) NOT NULL DEFAULT CURRENT_TIMESTAMP(3),
  updated_at DATETIME(3) NOT NULL DEFAULT CURRENT_TIMESTAMP(3) ON UPDATE CURRENT_TIMESTAMP(3),
  PRIMARY KEY (id),
  UNIQUE KEY uk_invoice__uuid (uuid),
  UNIQUE KEY uk_invoice__nf_key (nf_key),
  KEY idx_invoice__customer_status_date (customer_id, status, issue_date),
  KEY idx_invoice__origin_region (origin_region_id),
  KEY idx_invoice__dest_region (dest_region_id),
  CONSTRAINT fk_invoice__customer FOREIGN KEY (customer_id) REFERENCES customer(id)
    ON DELETE RESTRICT ON UPDATE RESTRICT,
  CONSTRAINT fk_invoice__origin_region FOREIGN KEY (origin_region_id) REFERENCES region(id)
    ON DELETE RESTRICT ON UPDATE RESTRICT,
  CONSTRAINT fk_invoice__dest_region FOREIGN KEY (dest_region_id) REFERENCES region(id)
    ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
