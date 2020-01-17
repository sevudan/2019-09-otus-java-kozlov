package ru.otus.hw12.model;

import com.google.gson.annotations.Expose;

import javax.persistence.*;

@Entity
@Table(name = "users")
public class User {

  @Id
  @Expose
  @GeneratedValue(strategy = GenerationType.SEQUENCE)
  @Column(name = "id", unique = true, nullable = false)
  private long id;

  //globally_quoted_identifiers enabled in a configuration file "cfg.cfg"
  @Expose
  @Column(name = "full_user_username", nullable = false)
  private String username;

  @Expose
  @Column(name = "login", nullable = false)
  private String login;

  @Expose
  @Column(name = "password", nullable = false)
  private String password;

  public User() { }

  public User(String username, String login, String password) {
    this.username = username;
    this.login = login;
    this.password = password;
  }

  public String getUsername() {
    return username;
  }

  public void setUsername(String username) {
    this.username = username;
  }

  public String getLogin() {
    return login;
  }

  public void setLogin(String login) {
    this.login = login;
  }

  public String getPassword() {
    return password;
  }


  public void setPassword(String password) {
    this.password = password;
  }

  public long getId() {
    return id;
  }

  public void setId(long id) {
    this.id = id;
  }

  @Override
  public String toString() {
    return "User{" +
            "id=" + id +
            ", username='" + username + '\'' +
            ", login='" + login + '\'' +
            ", password='" + password + '\'' +
            '}';
  }

}