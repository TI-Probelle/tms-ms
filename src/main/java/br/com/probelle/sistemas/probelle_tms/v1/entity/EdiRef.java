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
import br.com.probelle.sistemas.probelle_tms.v1.entity.enums.EdiRefType;
import java.time.LocalDateTime;
import java.util.UUID;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

@Entity
@Table(name = "edi_ref")
public class EdiRef {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @JdbcTypeCode(SqlTypes.BINARY)
  @Column(nullable = false, unique = true, updatable = false, insertable = false, columnDefinition = "BINARY(16)")
  private UUID uuid;

  @ManyToOne(optional = false)
  @JoinColumn(name = "edi_document_id", nullable = false)
  private EdiDocument ediDocument;

  @Enumerated(EnumType.STRING)
  @Column(name = "ref_type", nullable = false, length = 20)
  private EdiRefType refType;

  @Column(name = "ref_id", nullable = false)
  private Long refId;

  @Column(name = "created_at", insertable = false, updatable = false)
  private LocalDateTime createdAt;

  @Column(name = "updated_at", insertable = false, updatable = false)
  private LocalDateTime updatedAt;

  public EdiRef() {}

  private EdiRef(Builder builder) {
    this.ediDocument = builder.ediDocument;
    this.refType = builder.refType;
    this.refId = builder.refId;
  }

  public Long getId() {
    return id;
  }

  public UUID getUuid() {
    return uuid;
  }

  public EdiDocument getEdiDocument() {
    return ediDocument;
  }

  public void setEdiDocument(EdiDocument ediDocument) {
    this.ediDocument = ediDocument;
  }

  public EdiRefType getRefType() {
    return refType;
  }

  public void setRefType(EdiRefType refType) {
    this.refType = refType;
  }

  public Long getRefId() {
    return refId;
  }

  public void setRefId(Long refId) {
    this.refId = refId;
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
    private EdiDocument ediDocument;
    private EdiRefType refType;
    private Long refId;

    public Builder ediDocument(EdiDocument ediDocument) {
      this.ediDocument = ediDocument;
      return this;
    }

    public Builder refType(EdiRefType refType) {
      this.refType = refType;
      return this;
    }

    public Builder refId(Long refId) {
      this.refId = refId;
      return this;
    }

    public EdiRef build() {
      return new EdiRef(this);
    }
  }
}
