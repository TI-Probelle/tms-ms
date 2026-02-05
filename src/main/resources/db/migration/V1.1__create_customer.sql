-- Creates customer table
CREATE TABLE customer (
  id BIGINT UNSIGNED NOT NULL AUTO_INCREMENT,
  uuid BINARY(16) NOT NULL DEFAULT (UUID_TO_BIN(UUID())),
  code VARCHAR(40) NOT NULL,
  name VARCHAR(160) NOT NULL,
  default_region_id BIGINT UNSIGNED NULL,
  active BOOLEAN NOT NULL DEFAULT TRUE,
  created_at DATETIME(3) NOT NULL DEFAULT CURRENT_TIMESTAMP(3),
  updated_at DATETIME(3) NOT NULL DEFAULT CURRENT_TIMESTAMP(3) ON UPDATE CURRENT_TIMESTAMP(3),
  PRIMARY KEY (id),
  UNIQUE KEY uk_customer__uuid (uuid),
  UNIQUE KEY uk_customer__code (code),
  KEY idx_customer__default_region (default_region_id),
  CONSTRAINT fk_customer__default_region FOREIGN KEY (default_region_id) REFERENCES region(id)
    ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
