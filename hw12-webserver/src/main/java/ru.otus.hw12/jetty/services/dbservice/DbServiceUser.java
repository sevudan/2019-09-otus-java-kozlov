package ru.otus.hw12.jetty.services.dbservice;

import ru.otus.hw12.model.User;

import java.util.List;
import java.util.Optional;

public interface DbServiceUser {

  long saveUser(User user);

  Optional<User> findById(long id);

  Optional<User> findByLogin(String login);

  List<Optional<User>> getUsers();

}
