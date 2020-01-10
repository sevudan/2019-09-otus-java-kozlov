package ru.otus.hw10.api.model;

import javax.persistence.*;

@Entity
@Table(name = "phones")
public class PhoneDataSet {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "id", nullable = false)
  private long id;

  @Column(name = "number")
  private String number;

  public PhoneDataSet(){}

  public PhoneDataSet(String number) {
    this.number = number;
  }

  public String getNumber() {
    return number;
  }

  public void setNumber(String number) {
    this.number = number;
  }

  public long getId() {
    return id;
  }

  public void setId(long id) {
    this.id = id;
  }

  @Override
  public String toString() {
    return "PhoneDataSet{" +
            "id=" + id +
            ", number='" + number + '\'' +
            '}';
  }
}
