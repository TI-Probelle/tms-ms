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
import br.com.probelle.sistemas.probelle_tms.v1.entity.enums.EdiDirection;
import br.com.probelle.sistemas.probelle_tms.v1.entity.enums.EdiStatus;
import br.com.probelle.sistemas.probelle_tms.v1.entity.enums.EdiType;
import java.time.LocalDateTime;
import java.util.UUID;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

@Entity
@Table(name = "edi_document")
public class EdiDocument {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @JdbcTypeCode(SqlTypes.BINARY)
  @Column(nullable = false, unique = true, updatable = false, insertable = false, columnDefinition = "BINARY(16)")
  private UUID uuid;

  @ManyToOne(optional = false)
  @JoinColumn(name = "carrier_id", nullable = false)
  private Carrier carrier;

  @Enumerated(EnumType.STRING)
  @Column(name = "edi_type", nullable = false, length = 20)
  private EdiType ediType;

  @Column(length = 20)
  private String version;

  @Enumerated(EnumType.STRING)
  @Column(nullable = false, length = 10)
  private EdiDirection direction;

  @Column(name = "file_name", nullable = false, length = 255)
  private String fileName;

  @Column(name = "received_at")
  private LocalDateTime receivedAt;

  @Column(name = "generated_at")
  private LocalDateTime generatedAt;

  @Enumerated(EnumType.STRING)
  @Column(nullable = false, length = 10)
  private EdiStatus status;

  @Column(name = "raw_content", columnDefinition = "longtext")
  private String rawContent;

  @Column(name = "created_at", insertable = false, updatable = false)
  private LocalDateTime createdAt;

  @Column(name = "updated_at", insertable = false, updatable = false)
  private LocalDateTime updatedAt;

  public EdiDocument() {}

  private EdiDocument(Builder builder) {
    this.carrier = builder.carrier;
    this.ediType = builder.ediType;
    this.version = builder.version;
    this.direction = builder.direction;
    this.fileName = builder.fileName;
    this.receivedAt = builder.receivedAt;
    this.generatedAt = builder.generatedAt;
    this.status = builder.status;
    this.rawContent = builder.rawContent;
  }

  public Long getId() {
    return id;
  }

  public UUID getUuid() {
    return uuid;
  }

  public Carrier getCarrier() {
    return carrier;
  }

  public void setCarrier(Carrier carrier) {
    this.carrier = carrier;
  }

  public EdiType getEdiType() {
    return ediType;
  }

  public void setEdiType(EdiType ediType) {
    this.ediType = ediType;
  }

  public String getVersion() {
    return version;
  }

  public void setVersion(String version) {
    this.version = version;
  }

  public EdiDirection getDirection() {
    return direction;
  }

  public void setDirection(EdiDirection direction) {
    this.direction = direction;
  }

  public String getFileName() {
    return fileName;
  }

  public void setFileName(String fileName) {
    this.fileName = fileName;
  }

  public LocalDateTime getReceivedAt() {
    return receivedAt;
  }

  public void setReceivedAt(LocalDateTime receivedAt) {
    this.receivedAt = receivedAt;
  }

  public LocalDateTime getGeneratedAt() {
    return generatedAt;
  }

  public void setGeneratedAt(LocalDateTime generatedAt) {
    this.generatedAt = generatedAt;
  }

  public EdiStatus getStatus() {
    return status;
  }

  public void setStatus(EdiStatus status) {
    this.status = status;
  }

  public String getRawContent() {
    return rawContent;
  }

  public void setRawContent(String rawContent) {
    this.rawContent = rawContent;
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
    private Carrier carrier;
    private EdiType ediType;
    private String version;
    private EdiDirection direction;
    private String fileName;
    private LocalDateTime receivedAt;
    private LocalDateTime generatedAt;
    private EdiStatus status;
    private String rawContent;

    public Builder carrier(Carrier carrier) {
      this.carrier = carrier;
      return this;
    }

    public Builder ediType(EdiType ediType) {
      this.ediType = ediType;
      return this;
    }

    public Builder version(String version) {
      this.version = version;
      return this;
    }

    public Builder direction(EdiDirection direction) {
      this.direction = direction;
      return this;
    }

    public Builder fileName(String fileName) {
      this.fileName = fileName;
      return this;
    }

    public Builder receivedAt(LocalDateTime receivedAt) {
      this.receivedAt = receivedAt;
      return this;
    }

    public Builder generatedAt(LocalDateTime generatedAt) {
      this.generatedAt = generatedAt;
      return this;
    }

    public Builder status(EdiStatus status) {
      this.status = status;
      return this;
    }

    public Builder rawContent(String rawContent) {
      this.rawContent = rawContent;
      return this;
    }

    public EdiDocument build() {
      return new EdiDocument(this);
    }
  }
}
