package ru.otus.hw10.api.dao;

import java.util.Optional;

import ru.otus.hw10.api.model.User;
import ru.otus.hw10.api.sessionmanager.SessionManager;

public interface UserDao {

  Optional<User> findById(long id);

  long saveUser(User user);

  SessionManager getSessionManager();
}
