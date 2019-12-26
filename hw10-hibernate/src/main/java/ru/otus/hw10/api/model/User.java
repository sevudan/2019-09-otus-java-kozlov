package ru.otus.hw10.api.model;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;


@Entity
@Table(name = "users")
public class User {

  @Id
  @GeneratedValue(strategy = GenerationType.SEQUENCE)
  @Column(name = "id", unique = true, nullable = false)
  private long id;

  //globally_quoted_identifiers enabled in a configuration file "hibernate.cfg"
  @Column(name="name", nullable = false)
  private String name;

  @Column(name="age", nullable = false)
  private int age;

  @OneToOne(targetEntity = AddressDataSet.class,fetch = FetchType.EAGER,
          cascade = {CascadeType.ALL}, orphanRemoval = true)
  @JoinColumn(name = "address_id")
  private AddressDataSet addressSet;

  @OneToMany(targetEntity = PhoneDataSet.class, fetch = FetchType.EAGER, cascade = {CascadeType.PERSIST, CascadeType.MERGE}, orphanRemoval = true)
  @JoinColumn(name = "phone_id")
  private Set<PhoneDataSet> phoneDataSets= new HashSet<>();

  public User() {}

  public long getId() {
    return id;
  }

  public String getName() {
    return name;
  }

  public long getAge() {
    return age;
  }

  public void setId(long id) {
    this.id = id;
  }

  public void setName(String name) {
    this.name = name;
  }

  public void setAge(int age) {
    this.age = age;
  }

  public AddressDataSet getAddressSet() {
    return addressSet;
  }

  public void setAddressSet(AddressDataSet addressSet) {
    this.addressSet = addressSet;
  }

  public Set<PhoneDataSet> getPhoneDataSets() {
    return phoneDataSets;
  }

  public void setPhoneDataSets(Set<PhoneDataSet> phoneDataSets) {
    this.phoneDataSets = phoneDataSets;
  }

  @Override
  public String toString() {
    return "User{" +
            " id=" + id +
            ", name='" + name + '\'' +
            ", age='" + age + "\' }";
  }
}