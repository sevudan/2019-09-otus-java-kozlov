package ru.otus.hw12.hibernate.dbservice;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.otus.hw12.dao.UserDao;
import ru.otus.hw12.hibernate.sessionmanager.SessionManager;
import ru.otus.hw12.model.User;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class DbServiceUserImpl implements DbServiceUser {
  private static Logger logger = LoggerFactory.getLogger(DbServiceUserImpl.class);

  private final UserDao userDao;

  public DbServiceUserImpl(UserDao userDao) {
    this.userDao = userDao;
  }

  public long saveUser(User user) {
    try (SessionManager sessionManager = userDao.getSessionManager()) {
      sessionManager.beginSession();
      try {
        long userId = userDao.saveUser(user);
        sessionManager.commitSession();
        logger.info("created user: {}", userId);
        return userId;
      } catch (Exception e) {
        logger.error(e.getMessage(), e);
        sessionManager.rollbackSession();
        throw new DbServiceException(e);
      }
    }
  }

  @Override
  public Optional<User> findById(long id) {
    try (SessionManager sessionManager = userDao.getSessionManager()) {
      sessionManager.beginSession();
      try {
        Optional<User> userOptional = userDao.findById(id);
        logger.info("user: {}", userOptional.orElse(null));
        return userOptional;
      } catch (Exception e) {
        logger.error(e.getMessage(), e);
        sessionManager.rollbackSession();
      }
      return Optional.empty();
    }
  }

  @Override
  public Optional<User> findByLogin(String login) {
    try (SessionManager sessionManager = userDao.getSessionManager()) {
      sessionManager.beginSession();
      try {
        Optional<User> userOptional = userDao.findByLogin(login);

        logger.info("get user by login: {}", userOptional.orElse(null));
        return userOptional;
      } catch (Exception e) {
        logger.error(e.getMessage(), e);
        sessionManager.rollbackSession();
      }
      return Optional.empty();
    }
  }

  @Override
  public List<User> getUsers() {
    try (SessionManager sessionManager = userDao.getSessionManager()) {
      sessionManager.beginSession();
      try {
        List<User> users = userDao.getAllUsers();
        users.forEach(user -> logger.info("get user: {}", user));
        return users;
      } catch (Exception e) {
        logger.error(e.getMessage(), e);
        sessionManager.rollbackSession();
      }
      return new ArrayList<>();
    }
  }
}
