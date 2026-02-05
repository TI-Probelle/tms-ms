package br.com.probelle.sistemas.probelle_tms.v1.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import java.time.LocalDateTime;
import java.util.UUID;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

@Entity
@Table(name = "delivery_event")
public class DeliveryEvent {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @JdbcTypeCode(SqlTypes.BINARY)
  @Column(nullable = false, unique = true, updatable = false, insertable = false, columnDefinition = "BINARY(16)")
  private UUID uuid;

  @ManyToOne(optional = false)
  @JoinColumn(name = "manifest_id", nullable = false)
  private Manifest manifest;

  @ManyToOne
  @JoinColumn(name = "invoice_id")
  private Invoice invoice;

  @ManyToOne(optional = false)
  @JoinColumn(name = "edi_document_id", nullable = false)
  private EdiDocument ediDocument;

  @Column(name = "occurrence_code", nullable = false, length = 20)
  private String occurrenceCode;

  @Column(name = "occurrence_desc", nullable = false, length = 255)
  private String occurrenceDesc;

  @Column(name = "occurred_at", nullable = false)
  private LocalDateTime occurredAt;

  @Column(length = 255)
  private String note;

  @Column(name = "created_at", insertable = false, updatable = false)
  private LocalDateTime createdAt;

  @Column(name = "updated_at", insertable = false, updatable = false)
  private LocalDateTime updatedAt;

  public DeliveryEvent() {}

  private DeliveryEvent(Builder builder) {
    this.manifest = builder.manifest;
    this.invoice = builder.invoice;
    this.ediDocument = builder.ediDocument;
    this.occurrenceCode = builder.occurrenceCode;
    this.occurrenceDesc = builder.occurrenceDesc;
    this.occurredAt = builder.occurredAt;
    this.note = builder.note;
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

  public EdiDocument getEdiDocument() {
    return ediDocument;
  }

  public void setEdiDocument(EdiDocument ediDocument) {
    this.ediDocument = ediDocument;
  }

  public String getOccurrenceCode() {
    return occurrenceCode;
  }

  public void setOccurrenceCode(String occurrenceCode) {
    this.occurrenceCode = occurrenceCode;
  }

  public String getOccurrenceDesc() {
    return occurrenceDesc;
  }

  public void setOccurrenceDesc(String occurrenceDesc) {
    this.occurrenceDesc = occurrenceDesc;
  }

  public LocalDateTime getOccurredAt() {
    return occurredAt;
  }

  public void setOccurredAt(LocalDateTime occurredAt) {
    this.occurredAt = occurredAt;
  }

  public String getNote() {
    return note;
  }

  public void setNote(String note) {
    this.note = note;
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
    private EdiDocument ediDocument;
    private String occurrenceCode;
    private String occurrenceDesc;
    private LocalDateTime occurredAt;
    private String note;

    public Builder manifest(Manifest manifest) {
      this.manifest = manifest;
      return this;
    }

    public Builder invoice(Invoice invoice) {
      this.invoice = invoice;
      return this;
    }

    public Builder ediDocument(EdiDocument ediDocument) {
      this.ediDocument = ediDocument;
      return this;
    }

    public Builder occurrenceCode(String occurrenceCode) {
      this.occurrenceCode = occurrenceCode;
      return this;
    }

    public Builder occurrenceDesc(String occurrenceDesc) {
      this.occurrenceDesc = occurrenceDesc;
      return this;
    }

    public Builder occurredAt(LocalDateTime occurredAt) {
      this.occurredAt = occurredAt;
      return this;
    }

    public Builder note(String note) {
      this.note = note;
      return this;
    }

    public DeliveryEvent build() {
      return new DeliveryEvent(this);
    }
  }
}
