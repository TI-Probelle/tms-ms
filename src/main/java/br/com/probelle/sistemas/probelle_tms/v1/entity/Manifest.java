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
import br.com.probelle.sistemas.probelle_tms.v1.entity.enums.ManifestStatus;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

@Entity
@Table(name = "manifest")
public class Manifest {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @JdbcTypeCode(SqlTypes.BINARY)
  @Column(nullable = false, unique = true, updatable = false, insertable = false, columnDefinition = "BINARY(16)")
  private UUID uuid;

  @Column(name = "manifest_no", nullable = false, length = 40, unique = true)
  private String manifestNo;

  @ManyToOne(optional = false)
  @JoinColumn(name = "customer_id", nullable = false)
  private Customer customer;

  @ManyToOne(optional = false)
  @JoinColumn(name = "carrier_id", nullable = false)
  private Carrier carrier;

  @ManyToOne(optional = false)
  @JoinColumn(name = "origin_region_id", nullable = false)
  private Region originRegion;

  @ManyToOne(optional = false)
  @JoinColumn(name = "dest_region_id", nullable = false)
  private Region destRegion;

  @Enumerated(EnumType.STRING)
  @Column(nullable = false, length = 20)
  private ManifestStatus status;

  @Column(name = "locked_at")
  private LocalDateTime lockedAt;

  @Column(name = "total_weight_kg", nullable = false, precision = 15, scale = 3)
  private BigDecimal totalWeightKg;

  @Column(name = "total_value", nullable = false, precision = 15, scale = 2)
  private BigDecimal totalValue;

  @Column(name = "created_at", insertable = false, updatable = false)
  private LocalDateTime createdAt;

  @Column(name = "updated_at", insertable = false, updatable = false)
  private LocalDateTime updatedAt;

  public Manifest() {}

  private Manifest(Builder builder) {
    this.manifestNo = builder.manifestNo;
    this.customer = builder.customer;
    this.carrier = builder.carrier;
    this.originRegion = builder.originRegion;
    this.destRegion = builder.destRegion;
    this.status = builder.status;
    this.lockedAt = builder.lockedAt;
    this.totalWeightKg = builder.totalWeightKg;
    this.totalValue = builder.totalValue;
  }

  public Long getId() {
    return id;
  }

  public UUID getUuid() {
    return uuid;
  }

  public String getManifestNo() {
    return manifestNo;
  }

  public void setManifestNo(String manifestNo) {
    this.manifestNo = manifestNo;
  }

  public Customer getCustomer() {
    return customer;
  }

  public void setCustomer(Customer customer) {
    this.customer = customer;
  }

  public Carrier getCarrier() {
    return carrier;
  }

  public void setCarrier(Carrier carrier) {
    this.carrier = carrier;
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

  public ManifestStatus getStatus() {
    return status;
  }

  public void setStatus(ManifestStatus status) {
    this.status = status;
  }

  public LocalDateTime getLockedAt() {
    return lockedAt;
  }

  public void setLockedAt(LocalDateTime lockedAt) {
    this.lockedAt = lockedAt;
  }

  public BigDecimal getTotalWeightKg() {
    return totalWeightKg;
  }

  public void setTotalWeightKg(BigDecimal totalWeightKg) {
    this.totalWeightKg = totalWeightKg;
  }

  public BigDecimal getTotalValue() {
    return totalValue;
  }

  public void setTotalValue(BigDecimal totalValue) {
    this.totalValue = totalValue;
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
    private String manifestNo;
    private Customer customer;
    private Carrier carrier;
    private Region originRegion;
    private Region destRegion;
    private ManifestStatus status;
    private LocalDateTime lockedAt;
    private BigDecimal totalWeightKg;
    private BigDecimal totalValue;

    public Builder manifestNo(String manifestNo) {
      this.manifestNo = manifestNo;
      return this;
    }

    public Builder customer(Customer customer) {
      this.customer = customer;
      return this;
    }

    public Builder carrier(Carrier carrier) {
      this.carrier = carrier;
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

    public Builder status(ManifestStatus status) {
      this.status = status;
      return this;
    }

    public Builder lockedAt(LocalDateTime lockedAt) {
      this.lockedAt = lockedAt;
      return this;
    }

    public Builder totalWeightKg(BigDecimal totalWeightKg) {
      this.totalWeightKg = totalWeightKg;
      return this;
    }

    public Builder totalValue(BigDecimal totalValue) {
      this.totalValue = totalValue;
      return this;
    }

    public Manifest build() {
      return new Manifest(this);
    }
  }
}
