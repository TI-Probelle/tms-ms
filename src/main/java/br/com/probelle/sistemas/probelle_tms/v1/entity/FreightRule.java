package br.com.probelle.sistemas.probelle_tms.v1.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

@Entity
@Table(name = "freight_rule")
public class FreightRule {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @JdbcTypeCode(SqlTypes.BINARY)
  @Column(nullable = false, unique = true, updatable = false, insertable = false, columnDefinition = "BINARY(16)")
  private UUID uuid;

  @ManyToOne(optional = false)
  @JoinColumn(name = "freight_table_id", nullable = false)
  private FreightTable freightTable;

  @ManyToOne(optional = false)
  @JoinColumn(name = "origin_region_id", nullable = false)
  private Region originRegion;

  @ManyToOne(optional = false)
  @JoinColumn(name = "dest_region_id", nullable = false)
  private Region destRegion;

  @Column(name = "weight_break_from", nullable = false, precision = 15, scale = 3)
  private BigDecimal weightBreakFrom;

  @Column(name = "weight_break_to", nullable = false, precision = 15, scale = 3)
  private BigDecimal weightBreakTo;

  @Column(name = "rate_per_kg", nullable = false, precision = 15, scale = 4)
  private BigDecimal ratePerKg;

  @Column(name = "minimum_freight", nullable = false, precision = 15, scale = 2)
  private BigDecimal minimumFreight;

  @Column(name = "ad_valorem_pct", nullable = false, precision = 7, scale = 4)
  private BigDecimal adValoremPct;

  @Column(name = "gris_pct", nullable = false, precision = 7, scale = 4)
  private BigDecimal grisPct;

  @Column(name = "toll_fixed", nullable = false, precision = 15, scale = 2)
  private BigDecimal tollFixed;

  @Column(name = "dispatch_fixed", nullable = false, precision = 15, scale = 2)
  private BigDecimal dispatchFixed;

  @Column(name = "created_at", insertable = false, updatable = false)
  private LocalDateTime createdAt;

  @Column(name = "updated_at", insertable = false, updatable = false)
  private LocalDateTime updatedAt;

  public FreightRule() {}

  private FreightRule(Builder builder) {
    this.freightTable = builder.freightTable;
    this.originRegion = builder.originRegion;
    this.destRegion = builder.destRegion;
    this.weightBreakFrom = builder.weightBreakFrom;
    this.weightBreakTo = builder.weightBreakTo;
    this.ratePerKg = builder.ratePerKg;
    this.minimumFreight = builder.minimumFreight;
    this.adValoremPct = builder.adValoremPct;
    this.grisPct = builder.grisPct;
    this.tollFixed = builder.tollFixed;
    this.dispatchFixed = builder.dispatchFixed;
  }

  public Long getId() {
    return id;
  }

  public UUID getUuid() {
    return uuid;
  }

  public FreightTable getFreightTable() {
    return freightTable;
  }

  public void setFreightTable(FreightTable freightTable) {
    this.freightTable = freightTable;
  }

  public Region getOriginRegion() {
    return originRegion;
  }

  public void setOriginRegion(Region originRegion) {
    this.originRegion = originRegion;
  }

  public Region getDestRegion() {
    return destRegion;
  }

  public void setDestRegion(Region destRegion) {
    this.destRegion = destRegion;
  }

  public BigDecimal getWeightBreakFrom() {
    return weightBreakFrom;
  }

  public void setWeightBreakFrom(BigDecimal weightBreakFrom) {
    this.weightBreakFrom = weightBreakFrom;
  }

  public BigDecimal getWeightBreakTo() {
    return weightBreakTo;
  }

  public void setWeightBreakTo(BigDecimal weightBreakTo) {
    this.weightBreakTo = weightBreakTo;
  }

  public BigDecimal getRatePerKg() {
    return ratePerKg;
  }

  public void setRatePerKg(BigDecimal ratePerKg) {
    this.ratePerKg = ratePerKg;
  }

  public BigDecimal getMinimumFreight() {
    return minimumFreight;
  }

  public void setMinimumFreight(BigDecimal minimumFreight) {
    this.minimumFreight = minimumFreight;
  }

  public BigDecimal getAdValoremPct() {
    return adValoremPct;
  }

  public void setAdValoremPct(BigDecimal adValoremPct) {
    this.adValoremPct = adValoremPct;
  }

  public BigDecimal getGrisPct() {
    return grisPct;
  }

  public void setGrisPct(BigDecimal grisPct) {
    this.grisPct = grisPct;
  }

  public BigDecimal getTollFixed() {
    return tollFixed;
  }

  public void setTollFixed(BigDecimal tollFixed) {
    this.tollFixed = tollFixed;
  }

  public BigDecimal getDispatchFixed() {
    return dispatchFixed;
  }

  public void setDispatchFixed(BigDecimal dispatchFixed) {
    this.dispatchFixed = dispatchFixed;
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
    private FreightTable freightTable;
    private Region originRegion;
    private Region destRegion;
    private BigDecimal weightBreakFrom;
    private BigDecimal weightBreakTo;
    private BigDecimal ratePerKg;
    private BigDecimal minimumFreight;
    private BigDecimal adValoremPct;
    private BigDecimal grisPct;
    private BigDecimal tollFixed;
    private BigDecimal dispatchFixed;

    public Builder freightTable(FreightTable freightTable) {
      this.freightTable = freightTable;
      return this;
    }

    public Builder originRegion(Region originRegion) {
      this.originRegion = originRegion;
      return this;
    }

    public Builder destRegion(Region destRegion) {
      this.destRegion = destRegion;
      return this;
    }

    public Builder weightBreakFrom(BigDecimal weightBreakFrom) {
      this.weightBreakFrom = weightBreakFrom;
      return this;
    }

    public Builder weightBreakTo(BigDecimal weightBreakTo) {
      this.weightBreakTo = weightBreakTo;
      return this;
    }

    public Builder ratePerKg(BigDecimal ratePerKg) {
      this.ratePerKg = ratePerKg;
      return this;
    }

    public Builder minimumFreight(BigDecimal minimumFreight) {
      this.minimumFreight = minimumFreight;
      return this;
    }

    public Builder adValoremPct(BigDecimal adValoremPct) {
      this.adValoremPct = adValoremPct;
      return this;
    }

    public Builder grisPct(BigDecimal grisPct) {
      this.grisPct = grisPct;
      return this;
    }

    public Builder tollFixed(BigDecimal tollFixed) {
      this.tollFixed = tollFixed;
      return this;
    }

    public Builder dispatchFixed(BigDecimal dispatchFixed) {
      this.dispatchFixed = dispatchFixed;
      return this;
    }

    public FreightRule build() {
      return new FreightRule(this);
    }
  }
}
