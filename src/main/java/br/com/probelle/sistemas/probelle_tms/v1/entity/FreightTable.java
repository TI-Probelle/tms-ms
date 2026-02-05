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
import br.com.probelle.sistemas.probelle_tms.v1.entity.enums.FreightTableMode;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.UUID;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

@Entity
@Table(name = "freight_table")
public class FreightTable {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @JdbcTypeCode(SqlTypes.BINARY)
  @Column(nullable = false, unique = true, updatable = false, insertable = false, columnDefinition = "BINARY(16)")
  private UUID uuid;

  @ManyToOne(optional = false)
  @JoinColumn(name = "carrier_id", nullable = false)
  private Carrier carrier;

  @Column(nullable = false, length = 120)
  private String name;

  @Enumerated(EnumType.STRING)
  @Column(nullable = false, length = 10)
  private FreightTableMode mode;

  @Column(name = "valid_from", nullable = false)
  private LocalDate validFrom;

  @Column(name = "valid_to", nullable = false)
  private LocalDate validTo;

  @Column(nullable = false)
  private Boolean active;

  @Column(name = "created_at", insertable = false, updatable = false)
  private LocalDateTime createdAt;

  @Column(name = "updated_at", insertable = false, updatable = false)
  private LocalDateTime updatedAt;

  public FreightTable() {}

  private FreightTable(Builder builder) {
    this.carrier = builder.carrier;
    this.name = builder.name;
    this.mode = builder.mode;
    this.validFrom = builder.validFrom;
    this.validTo = builder.validTo;
    this.active = builder.active;
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

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public FreightTableMode getMode() {
    return mode;
  }

  public void setMode(FreightTableMode mode) {
    this.mode = mode;
  }

  public LocalDate getValidFrom() {
    return validFrom;
  }

  public void setValidFrom(LocalDate validFrom) {
    this.validFrom = validFrom;
  }

  public LocalDate getValidTo() {
    return validTo;
  }

  public void setValidTo(LocalDate validTo) {
    this.validTo = validTo;
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
    private Carrier carrier;
    private String name;
    private FreightTableMode mode;
    private LocalDate validFrom;
    private LocalDate validTo;
    private Boolean active;

    public Builder carrier(Carrier carrier) {
      this.carrier = carrier;
      return this;
    }

    public Builder name(String name) {
      this.name = name;
      return this;
    }

    public Builder mode(FreightTableMode mode) {
      this.mode = mode;
      return this;
    }

    public Builder validFrom(LocalDate validFrom) {
      this.validFrom = validFrom;
      return this;
    }

    public Builder validTo(LocalDate validTo) {
      this.validTo = validTo;
      return this;
    }

    public Builder active(Boolean active) {
      this.active = active;
      return this;
    }

    public FreightTable build() {
      return new FreightTable(this);
    }
  }
}
