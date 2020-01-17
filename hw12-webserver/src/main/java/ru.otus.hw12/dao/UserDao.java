package ru.otus.hw12.dao;

import ru.otus.hw12.hibernate.sessionmanager.SessionManager;
import ru.otus.hw12.model.User;

import java.util.List;
import java.util.Optional;

public interface UserDao {

  Optional<User> findById(long id);

  Optional<User> findByLogin(String login);

  List<Optional<User>> getAllUsers();

  long saveUser(User user);

  SessionManager getSessionManager();
}
