package br.com.probelle.sistemas.probelle_tms.v1.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import br.com.probelle.sistemas.probelle_tms.v1.entity.enums.CarrierType;
import java.time.LocalDateTime;
import java.util.UUID;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

@Entity
@Table(name = "carrier")
public class Carrier {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @JdbcTypeCode(SqlTypes.BINARY)
  @Column(nullable = false, unique = true, updatable = false, insertable = false, columnDefinition = "BINARY(16)")
  private UUID uuid;

  @Column(nullable = false, length = 40, unique = true)
  private String code;

  @Column(nullable = false, length = 160)
  private String name;

  @Enumerated(EnumType.STRING)
  @Column(name = "carrier_type", nullable = false, length = 20)
  private CarrierType carrierType;

  @Column(name = "doc_number", length = 20)
  private String docNumber;

  @Column(nullable = false)
  private Boolean active;

  @Column(name = "created_at", insertable = false, updatable = false)
  private LocalDateTime createdAt;

  @Column(name = "updated_at", insertable = false, updatable = false)
  private LocalDateTime updatedAt;

  public Carrier() {}

  private Carrier(Builder builder) {
    this.code = builder.code;
    this.name = builder.name;
    this.carrierType = builder.carrierType;
    this.docNumber = builder.docNumber;
    this.active = builder.active;
  }

  public Long getId() {
    return id;
  }

  public UUID getUuid() {
    return uuid;
  }

  public String getCode() {
    return code;
  }

  public void setCode(String code) {
    this.code = code;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public CarrierType getCarrierType() {
    return carrierType;
  }

  public void setCarrierType(CarrierType carrierType) {
    this.carrierType = carrierType;
  }

  public String getDocNumber() {
    return docNumber;
  }

  public void setDocNumber(String docNumber) {
    this.docNumber = docNumber;
  }

  public Boolean getActive() {
    return active;
  }

  public void setActive(Boolean active) {
    this.active = active;
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
    private String code;
    private String name;
    private CarrierType carrierType;
    private String docNumber;
    private Boolean active;

    public Builder code(String code) {
      this.code = code;
      return this;
    }

    public Builder name(String name) {
      this.name = name;
      return this;
    }

    public Builder carrierType(CarrierType carrierType) {
      this.carrierType = carrierType;
      return this;
    }

    public Builder docNumber(String docNumber) {
      this.docNumber = docNumber;
      return this;
    }

    public Builder active(Boolean active) {
      this.active = active;
      return this;
    }

    public Carrier build() {
      return new Carrier(this);
    }
  }
}
