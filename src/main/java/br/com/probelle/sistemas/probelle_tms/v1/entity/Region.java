package br.com.probelle.sistemas.probelle_tms.v1.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import java.time.LocalDateTime;
import java.util.UUID;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

@Entity
@Table(name = "region")
public class Region {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @JdbcTypeCode(SqlTypes.BINARY)
  @Column(nullable = false, unique = true, updatable = false, insertable = false, columnDefinition = "BINARY(16)")
  private UUID uuid;

  @Column(nullable = false, length = 30, unique = true)
  private String code;

  @Column(nullable = false, length = 120)
  private String name;

  @Column(length = 2)
  private String country;

  @Column(length = 10)
  private String state;

  @Column(length = 80)
  private String city;

  @Column(name = "created_at", insertable = false, updatable = false)
  private LocalDateTime createdAt;

  @Column(name = "updated_at", insertable = false, updatable = false)
  private LocalDateTime updatedAt;

  public Region() {}

  private Region(Builder builder) {
    this.code = builder.code;
    this.name = builder.name;
    this.country = builder.country;
    this.state = builder.state;
    this.city = builder.city;
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

  public String getCountry() {
    return country;
  }

  public void setCountry(String country) {
    this.country = country;
  }

  public String getState() {
    return state;
  }

  public void setState(String state) {
    this.state = state;
  }

  public String getCity() {
    return city;
  }

  public void setCity(String city) {
    this.city = city;
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
    private String country;
    private String state;
    private String city;

    public Builder code(String code) {
      this.code = code;
      return this;
    }

    public Builder name(String name) {
      this.name = name;
      return this;
    }

    public Builder country(String country) {
      this.country = country;
      return this;
    }

    public Builder state(String state) {
      this.state = state;
      return this;
    }

    public Builder city(String city) {
      this.city = city;
      return this;
    }

    public Region build() {
      return new Region(this);
    }
  }
}
