package ru.otus.hw11.api.model;

import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Id;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Column;

@Entity
@Table(name = "address")
public class AddressDataSet {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "id", nullable = false, unique = true)
  private long id;

  private String street;

  public AddressDataSet() {}

  public AddressDataSet(String street) {
    this.street = street;
  }

  public String getStreet() {
    return street;
  }

  public long getId() {
    return id;
  }

  public void setStreet(String street) {
    this.street = street;
  }

  @Override
  public String toString() {
    return "AddressDataSet{" +
            "id=" + id +
            ", street='" + street + '\'' +
            '}';
  }
}
