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
@Table(name = "carrier_driver")
public class CarrierDriver {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @JdbcTypeCode(SqlTypes.BINARY)
  @Column(nullable = false, unique = true, updatable = false, insertable = false, columnDefinition = "BINARY(16)")
  private UUID uuid;

  @ManyToOne(optional = false)
  @JoinColumn(name = "carrier_id", nullable = false)
  private Carrier carrier;

  @Column(nullable = false, length = 160)
  private String name;

  @Column(name = "doc_number", length = 20)
  private String docNumber;

  @Column(name = "plate_main", length = 10)
  private String plateMain;

  @Column(name = "plate_trailer", length = 10)
  private String plateTrailer;

  @Column(nullable = false)
  private Boolean active;

  @Column(name = "created_at", insertable = false, updatable = false)
  private LocalDateTime createdAt;

  @Column(name = "updated_at", insertable = false, updatable = false)
  private LocalDateTime updatedAt;

  public CarrierDriver() {}

  private CarrierDriver(Builder builder) {
    this.carrier = builder.carrier;
    this.name = builder.name;
    this.docNumber = builder.docNumber;
    this.plateMain = builder.plateMain;
    this.plateTrailer = builder.plateTrailer;
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

  public String getDocNumber() {
    return docNumber;
  }

  public void setDocNumber(String docNumber) {
    this.docNumber = docNumber;
  }

  public String getPlateMain() {
    return plateMain;
  }

  public void setPlateMain(String plateMain) {
    this.plateMain = plateMain;
  }

  public String getPlateTrailer() {
    return plateTrailer;
  }

  public void setPlateTrailer(String plateTrailer) {
    this.plateTrailer = plateTrailer;
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
    private String docNumber;
    private String plateMain;
    private String plateTrailer;
    private Boolean active;

    public Builder carrier(Carrier carrier) {
      this.carrier = carrier;
      return this;
    }

    public Builder name(String name) {
      this.name = name;
      return this;
    }

    public Builder docNumber(String docNumber) {
      this.docNumber = docNumber;
      return this;
    }

    public Builder plateMain(String plateMain) {
      this.plateMain = plateMain;
      return this;
    }

    public Builder plateTrailer(String plateTrailer) {
      this.plateTrailer = plateTrailer;
      return this;
    }

    public Builder active(Boolean active) {
      this.active = active;
      return this;
    }

    public CarrierDriver build() {
      return new CarrierDriver(this);
    }
  }
}
