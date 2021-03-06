package ru.otus.api.dao;

import java.util.Optional;

import ru.otus.api.model.User;
import ru.otus.api.sessionmanager.SessionManager;

public interface UserDao {

  Optional<User> findById(long id);

  long saveUser(User user);

  SessionManager getSessionManager();
}
