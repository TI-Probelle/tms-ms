package br.com.probelle.sistemas.probelle_tms.v1.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import br.com.probelle.sistemas.probelle_tms.v1.entity.enums.ManifestFreightPricingMode;
import br.com.probelle.sistemas.probelle_tms.v1.entity.enums.ManifestFreightPricingSource;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

@Entity
@Table(name = "manifest_freight")
public class ManifestFreight {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @JdbcTypeCode(SqlTypes.BINARY)
  @Column(nullable = false, unique = true, updatable = false, insertable = false, columnDefinition = "BINARY(16)")
  private UUID uuid;

  @ManyToOne(optional = false)
  @JoinColumn(name = "manifest_id", nullable = false)
  private Manifest manifest;

  @Enumerated(EnumType.STRING)
  @Column(name = "pricing_source", nullable = false, length = 20)
  private ManifestFreightPricingSource pricingSource;

  @Enumerated(EnumType.STRING)
  @Column(name = "pricing_mode", nullable = false, length = 10)
  private ManifestFreightPricingMode pricingMode;

  @ManyToOne
  @JoinColumn(name = "freight_table_id")
  private FreightTable freightTable;

  @Column(name = "base_weight_kg", nullable = false, precision = 15, scale = 3)
  private BigDecimal baseWeightKg;

  @Column(name = "base_value", nullable = false, precision = 15, scale = 2)
  private BigDecimal baseValue;

  @Column(name = "freight_total", nullable = false, precision = 15, scale = 2)
  private BigDecimal freightTotal;

  @Column(name = "breakdown_json", columnDefinition = "json")
  private String breakdownJson;

  @Column(name = "negotiated_by_user_id")
  private Long negotiatedByUserId;

  @Column(name = "negotiated_at")
  private LocalDateTime negotiatedAt;

  @Column(length = 500)
  private String notes;

  @Column(name = "created_at", insertable = false, updatable = false)
  private LocalDateTime createdAt;

  @Column(name = "updated_at", insertable = false, updatable = false)
  private LocalDateTime updatedAt;

  public ManifestFreight() {}

  private ManifestFreight(Builder builder) {
    this.manifest = builder.manifest;
    this.pricingSource = builder.pricingSource;
    this.pricingMode = builder.pricingMode;
    this.freightTable = builder.freightTable;
    this.baseWeightKg = builder.baseWeightKg;
    this.baseValue = builder.baseValue;
    this.freightTotal = builder.freightTotal;
    this.breakdownJson = builder.breakdownJson;
    this.negotiatedByUserId = builder.negotiatedByUserId;
    this.negotiatedAt = builder.negotiatedAt;
    this.notes = builder.notes;
  }

  public Long getId() {
    return id;
  }

  public UUID getUuid() {
    return uuid;
  }

  public Manifest getManifest() {
    return manifest;
  }

  public void setManifest(Manifest manifest) {
    this.manifest = manifest;
  }

  public ManifestFreightPricingSource getPricingSource() {
    return pricingSource;
  }

  public void setPricingSource(ManifestFreightPricingSource pricingSource) {
    this.pricingSource = pricingSource;
  }

  public ManifestFreightPricingMode getPricingMode() {
    return pricingMode;
  }

  public void setPricingMode(ManifestFreightPricingMode pricingMode) {
    this.pricingMode = pricingMode;
  }

  public FreightTable getFreightTable() {
    return freightTable;
  }

  public void setFreightTable(FreightTable freightTable) {
    this.freightTable = freightTable;
  }

  public BigDecimal getBaseWeightKg() {
    return baseWeightKg;
  }

  public void setBaseWeightKg(BigDecimal baseWeightKg) {
    this.baseWeightKg = baseWeightKg;
  }

  public BigDecimal getBaseValue() {
    return baseValue;
  }

  public void setBaseValue(BigDecimal baseValue) {
    this.baseValue = baseValue;
  }

  public BigDecimal getFreightTotal() {
    return freightTotal;
  }

  public void setFreightTotal(BigDecimal freightTotal) {
    this.freightTotal = freightTotal;
  }

  public String getBreakdownJson() {
    return breakdownJson;
  }

  public void setBreakdownJson(String breakdownJson) {
    this.breakdownJson = breakdownJson;
  }

  public Long getNegotiatedByUserId() {
    return negotiatedByUserId;
  }

  public void setNegotiatedByUserId(Long negotiatedByUserId) {
    this.negotiatedByUserId = negotiatedByUserId;
  }

  public LocalDateTime getNegotiatedAt() {
    return negotiatedAt;
  }

  public void setNegotiatedAt(LocalDateTime negotiatedAt) {
    this.negotiatedAt = negotiatedAt;
  }

  public String getNotes() {
    return notes;
  }

  public void setNotes(String notes) {
    this.notes = notes;
  }

  public LocalDateTime getCreatedAt() {
    return createdAt;
  }

  public LocalDateTime getUpdatedAt() {
    return updatedAt;
  }

  public static Builder builder() {
    return new Builder();
  }

  public static class Builder {
    private Manifest manifest;
    private ManifestFreightPricingSource pricingSource;
    private ManifestFreightPricingMode pricingMode;
    private FreightTable freightTable;
    private BigDecimal baseWeightKg;
    private BigDecimal baseValue;
    private BigDecimal freightTotal;
    private String breakdownJson;
    private Long negotiatedByUserId;
    private LocalDateTime negotiatedAt;
    private String notes;

    public Builder manifest(Manifest manifest) {
      this.manifest = manifest;
      return this;
    }

    public Builder pricingSource(ManifestFreightPricingSource pricingSource) {
      this.pricingSource = pricingSource;
      return this;
    }

    public Builder pricingMode(ManifestFreightPricingMode pricingMode) {
      this.pricingMode = pricingMode;
      return this;
    }

    public Builder freightTable(FreightTable freightTable) {
      this.freightTable = freightTable;
      return this;
    }

    public Builder baseWeightKg(BigDecimal baseWeightKg) {
      this.baseWeightKg = baseWeightKg;
      return this;
    }

    public Builder baseValue(BigDecimal baseValue) {
      this.baseValue = baseValue;
      return this;
    }

    public Builder freightTotal(BigDecimal freightTotal) {
      this.freightTotal = freightTotal;
      return this;
    }

    public Builder breakdownJson(String breakdownJson) {
      this.breakdownJson = breakdownJson;
      return this;
    }

    public Builder negotiatedByUserId(Long negotiatedByUserId) {
      this.negotiatedByUserId = negotiatedByUserId;
      return this;
    }

    public Builder negotiatedAt(LocalDateTime negotiatedAt) {
      this.negotiatedAt = negotiatedAt;
      return this;
    }

    public Builder notes(String notes) {
      this.notes = notes;
      return this;
    }

    public ManifestFreight build() {
      return new ManifestFreight(this);
    }
  }
}
