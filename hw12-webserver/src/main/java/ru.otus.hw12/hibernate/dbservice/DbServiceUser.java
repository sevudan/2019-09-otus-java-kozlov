package ru.otus.hw12.hibernate.dbservice;

import ru.otus.hw12.model.User;

import java.util.List;
import java.util.Optional;

public interface DbServiceUser {

  long saveUser(User user);

  Optional<User> findById(long id);

  Optional<User> findByLogin(String login);

  List<User> getUsers();

}
