-- Creates freight_rule table
CREATE TABLE freight_rule (
  id BIGINT UNSIGNED NOT NULL AUTO_INCREMENT,
  uuid BINARY(16) NOT NULL DEFAULT (UUID_TO_BIN(UUID())),
  freight_table_id BIGINT UNSIGNED NOT NULL,
  origin_region_id BIGINT UNSIGNED NOT NULL,
  dest_region_id BIGINT UNSIGNED NOT NULL,
  weight_break_from DECIMAL(15,3) NOT NULL DEFAULT 0,
  weight_break_to DECIMAL(15,3) NOT NULL DEFAULT 0,
  rate_per_kg DECIMAL(15,4) NOT NULL DEFAULT 0,
  minimum_freight DECIMAL(15,2) NOT NULL DEFAULT 0,
  ad_valorem_pct DECIMAL(7,4) NOT NULL DEFAULT 0,
  gris_pct DECIMAL(7,4) NOT NULL DEFAULT 0,
  toll_fixed DECIMAL(15,2) NOT NULL DEFAULT 0,
  dispatch_fixed DECIMAL(15,2) NOT NULL DEFAULT 0,
  created_at DATETIME(3) NOT NULL DEFAULT CURRENT_TIMESTAMP(3),
  updated_at DATETIME(3) NOT NULL DEFAULT CURRENT_TIMESTAMP(3) ON UPDATE CURRENT_TIMESTAMP(3),
  PRIMARY KEY (id),
  UNIQUE KEY uk_freight_rule__uuid (uuid),
  KEY idx_freight_rule__lookup (freight_table_id, origin_region_id, dest_region_id, weight_break_from, weight_break_to),
  KEY idx_freight_rule__origin_region (origin_region_id),
  KEY idx_freight_rule__dest_region (dest_region_id),
  CONSTRAINT fk_freight_rule__freight_table FOREIGN KEY (freight_table_id) REFERENCES freight_table(id)
    ON DELETE RESTRICT ON UPDATE RESTRICT,
  CONSTRAINT fk_freight_rule__origin_region FOREIGN KEY (origin_region_id) REFERENCES region(id)
    ON DELETE RESTRICT ON UPDATE RESTRICT,
  CONSTRAINT fk_freight_rule__dest_region FOREIGN KEY (dest_region_id) REFERENCES region(id)
    ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
