package ru.otus.api.model;

import ru.otus.orm.Id;

public class User {
  @Id
  private long id;
  private String name;
  private int age;


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

  @Override
  public String toString() {
    return "User{" +
        "id=" + id +
        ", name='" + name + '\'' +
        ", age='" + age + "\' }";
  }
}