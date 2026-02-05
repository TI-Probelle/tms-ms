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
@Table(name = "customer")
public class Customer {

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

  @ManyToOne
  @JoinColumn(name = "default_region_id")
  private Region defaultRegion;

  @Column(nullable = false)
  private Boolean active;

  @Column(name = "created_at", insertable = false, updatable = false)
  private LocalDateTime createdAt;

  @Column(name = "updated_at", insertable = false, updatable = false)
  private LocalDateTime updatedAt;

  public Customer() {}

  private Customer(Builder builder) {
    this.code = builder.code;
    this.name = builder.name;
    this.defaultRegion = builder.defaultRegion;
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

  public Region getDefaultRegion() {
    return defaultRegion;
  }

  public void setDefaultRegion(Region defaultRegion) {
    this.defaultRegion = defaultRegion;
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
    private Region defaultRegion;
    private Boolean active;

    public Builder code(String code) {
      this.code = code;
      return this;
    }

    public Builder name(String name) {
      this.name = name;
      return this;
    }

    public Builder defaultRegion(Region defaultRegion) {
      this.defaultRegion = defaultRegion;
      return this;
    }

    public Builder active(Boolean active) {
      this.active = active;
      return this;
    }

    public Customer build() {
      return new Customer(this);
    }
  }
}
