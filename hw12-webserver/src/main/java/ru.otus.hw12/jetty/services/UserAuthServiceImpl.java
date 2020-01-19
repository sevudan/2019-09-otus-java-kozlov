package ru.otus.hw12.jetty.services;

import ru.otus.hw12.hibernate.dbservice.DbServiceUser;


public class UserAuthServiceImpl implements UserAuthService {

  private final DbServiceUser dbServiceUser;

  public UserAuthServiceImpl(DbServiceUser dbServiceUser) {
    this.dbServiceUser = dbServiceUser;
  }

  @Override
  public boolean authenticate(String login, String password) {

    return dbServiceUser.findByLogin(login)
            .map(user -> user.getPassword().equals(password))
            .orElse(false);
  }
}
