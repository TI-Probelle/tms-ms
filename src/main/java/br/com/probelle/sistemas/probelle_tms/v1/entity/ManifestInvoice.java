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
@Table(name = "manifest_invoice")
public class ManifestInvoice {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @JdbcTypeCode(SqlTypes.BINARY)
  @Column(nullable = false, unique = true, updatable = false, insertable = false, columnDefinition = "BINARY(16)")
  private UUID uuid;

  @ManyToOne(optional = false)
  @JoinColumn(name = "manifest_id", nullable = false)
  private Manifest manifest;

  @ManyToOne(optional = false)
  @JoinColumn(name = "invoice_id", nullable = false)
  private Invoice invoice;

  @Column(nullable = false)
  private Integer seq;

  @Column(name = "weight_kg", nullable = false, precision = 15, scale = 3)
  private BigDecimal weightKg;

  @Column(nullable = false, precision = 15, scale = 2)
  private BigDecimal value;

  @Column(name = "linked_at", nullable = false)
  private LocalDateTime linkedAt;

  @Column(name = "created_at", insertable = false, updatable = false)
  private LocalDateTime createdAt;

  @Column(name = "updated_at", insertable = false, updatable = false)
  private LocalDateTime updatedAt;

  public ManifestInvoice() {}

  private ManifestInvoice(Builder builder) {
    this.manifest = builder.manifest;
    this.invoice = builder.invoice;
    this.seq = builder.seq;
    this.weightKg = builder.weightKg;
    this.value = builder.value;
    this.linkedAt = builder.linkedAt;
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

  public Invoice getInvoice() {
    return invoice;
  }

  public void setInvoice(Invoice invoice) {
    this.invoice = invoice;
  }

  public Integer getSeq() {
    return seq;
  }

  public void setSeq(Integer seq) {
    this.seq = seq;
  }

  public BigDecimal getWeightKg() {
    return weightKg;
  }

  public void setWeightKg(BigDecimal weightKg) {
    this.weightKg = weightKg;
  }

  public BigDecimal getValue() {
    return value;
  }

  public void setValue(BigDecimal value) {
    this.value = value;
  }

  public LocalDateTime getLinkedAt() {
    return linkedAt;
  }

  public void setLinkedAt(LocalDateTime linkedAt) {
    this.linkedAt = linkedAt;
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
    private Invoice invoice;
    private Integer seq;
    private BigDecimal weightKg;
    private BigDecimal value;
    private LocalDateTime linkedAt;

    public Builder manifest(Manifest manifest) {
      this.manifest = manifest;
      return this;
    }

    public Builder invoice(Invoice invoice) {
      this.invoice = invoice;
      return this;
    }

    public Builder seq(Integer seq) {
      this.seq = seq;
      return this;
    }

    public Builder weightKg(BigDecimal weightKg) {
      this.weightKg = weightKg;
      return this;
    }

    public Builder value(BigDecimal value) {
      this.value = value;
      return this;
    }

    public Builder linkedAt(LocalDateTime linkedAt) {
      this.linkedAt = linkedAt;
      return this;
    }

    public ManifestInvoice build() {
      return new ManifestInvoice(this);
    }
  }
}
