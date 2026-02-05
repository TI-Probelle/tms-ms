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
import br.com.probelle.sistemas.probelle_tms.v1.entity.enums.InvoiceStatus;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.UUID;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

@Entity
@Table(name = "invoice")
public class Invoice {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @JdbcTypeCode(SqlTypes.BINARY)
  @Column(nullable = false, unique = true, updatable = false, insertable = false, columnDefinition = "BINARY(16)")
  private UUID uuid;

  @Column(name = "nf_key", nullable = false, length = 44, unique = true)
  private String nfKey;

  @Column(name = "nf_number", nullable = false, length = 20)
  private String nfNumber;

  @Column(nullable = false, length = 10)
  private String series;

  @Column(nullable = false, length = 5)
  private String model;

  @Column(name = "issue_date", nullable = false)
  private LocalDate issueDate;

  @ManyToOne(optional = false)
  @JoinColumn(name = "customer_id", nullable = false)
  private Customer customer;

  @ManyToOne(optional = false)
  @JoinColumn(name = "origin_region_id", nullable = false)
  private Region originRegion;

  @ManyToOne(optional = false)
  @JoinColumn(name = "dest_region_id", nullable = false)
  private Region destRegion;

  @Column(name = "gross_value", nullable = false, precision = 15, scale = 2)
  private BigDecimal grossValue;

  @Column(name = "net_value", nullable = false, precision = 15, scale = 2)
  private BigDecimal netValue;

  @Column(name = "weight_kg", nullable = false, precision = 15, scale = 3)
  private BigDecimal weightKg;

  @Column(name = "volume_qty", nullable = false)
  private Integer volumeQty;

  @Enumerated(EnumType.STRING)
  @Column(nullable = false, length = 10)
  private InvoiceStatus status;

  @Column(name = "created_at", insertable = false, updatable = false)
  private LocalDateTime createdAt;

  @Column(name = "updated_at", insertable = false, updatable = false)
  private LocalDateTime updatedAt;

  public Invoice() {}

  private Invoice(Builder builder) {
    this.nfKey = builder.nfKey;
    this.nfNumber = builder.nfNumber;
    this.series = builder.series;
    this.model = builder.model;
    this.issueDate = builder.issueDate;
    this.customer = builder.customer;
    this.originRegion = builder.originRegion;
    this.destRegion = builder.destRegion;
    this.grossValue = builder.grossValue;
    this.netValue = builder.netValue;
    this.weightKg = builder.weightKg;
    this.volumeQty = builder.volumeQty;
    this.status = builder.status;
  }

  public Long getId() {
    return id;
  }

  public UUID getUuid() {
    return uuid;
  }

  public String getNfKey() {
    return nfKey;
  }

  public void setNfKey(String nfKey) {
    this.nfKey = nfKey;
  }

  public String getNfNumber() {
    return nfNumber;
  }

  public void setNfNumber(String nfNumber) {
    this.nfNumber = nfNumber;
  }

  public String getSeries() {
    return series;
  }

  public void setSeries(String series) {
    this.series = series;
  }

  public String getModel() {
    return model;
  }

  public void setModel(String model) {
    this.model = model;
  }

  public LocalDate getIssueDate() {
    return issueDate;
  }

  public void setIssueDate(LocalDate issueDate) {
    this.issueDate = issueDate;
  }

  public Customer getCustomer() {
    return customer;
  }

  public void setCustomer(Customer customer) {
    this.customer = customer;
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

  public BigDecimal getGrossValue() {
    return grossValue;
  }

  public void setGrossValue(BigDecimal grossValue) {
    this.grossValue = grossValue;
  }

  public BigDecimal getNetValue() {
    return netValue;
  }

  public void setNetValue(BigDecimal netValue) {
    this.netValue = netValue;
  }

  public BigDecimal getWeightKg() {
    return weightKg;
  }

  public void setWeightKg(BigDecimal weightKg) {
    this.weightKg = weightKg;
  }

  public Integer getVolumeQty() {
    return volumeQty;
  }

  public void setVolumeQty(Integer volumeQty) {
    this.volumeQty = volumeQty;
  }

  public InvoiceStatus getStatus() {
    return status;
  }

  public void setStatus(InvoiceStatus status) {
    this.status = status;
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
    private String nfKey;
    private String nfNumber;
    private String series;
    private String model;
    private LocalDate issueDate;
    private Customer customer;
    private Region originRegion;
    private Region destRegion;
    private BigDecimal grossValue;
    private BigDecimal netValue;
    private BigDecimal weightKg;
    private Integer volumeQty;
    private InvoiceStatus status;

    public Builder nfKey(String nfKey) {
      this.nfKey = nfKey;
      return this;
    }

    public Builder nfNumber(String nfNumber) {
      this.nfNumber = nfNumber;
      return this;
    }

    public Builder series(String series) {
      this.series = series;
      return this;
    }

    public Builder model(String model) {
      this.model = model;
      return this;
    }

    public Builder issueDate(LocalDate issueDate) {
      this.issueDate = issueDate;
      return this;
    }

    public Builder customer(Customer customer) {
      this.customer = customer;
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

    public Builder grossValue(BigDecimal grossValue) {
      this.grossValue = grossValue;
      return this;
    }

    public Builder netValue(BigDecimal netValue) {
      this.netValue = netValue;
      return this;
    }

    public Builder weightKg(BigDecimal weightKg) {
      this.weightKg = weightKg;
      return this;
    }

    public Builder volumeQty(Integer volumeQty) {
      this.volumeQty = volumeQty;
      return this;
    }

    public Builder status(InvoiceStatus status) {
      this.status = status;
      return this;
    }

    public Invoice build() {
      return new Invoice(this);
    }
  }
}
