package ru.otus.hw11.api.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.otus.hw11.api.dao.UserDao;
import ru.otus.hw11.api.model.User;
import ru.otus.hw11.api.sessionmanager.SessionManager;
import ru.otus.hw11.cache.HwCache;
import ru.otus.hw11.cache.HwCacheImpl;

import java.util.Optional;

public class DbServiceUserWithCacheImpl implements DBServiceUser {
  private static Logger logger = LoggerFactory.getLogger(DbServiceUserWithCacheImpl.class);

  private final UserDao userDao;
  private HwCache<String, User> cache;

  public DbServiceUserWithCacheImpl(UserDao userDao, HwCache cache) {
    this.userDao = userDao;
    this.cache = cache;
  }

  @Override
  public long saveUser(User user) {
    try (SessionManager sessionManager = userDao.getSessionManager()) {
      sessionManager.beginSession();
      try {
        long userId = userDao.saveUser(user);
        cache.put(String.valueOf(userId), user);
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
  public Optional<User> getUser(long id) {
    try (SessionManager sessionManager = userDao.getSessionManager()) {
      sessionManager.beginSession();
      try {
        User user = cache.get(String.valueOf(id));
        if (user == null) {
          Optional<User> userOptional = userDao.findById(id);
          logger.info("user get from DB: {}", userOptional.orElse(null));
          return userOptional;
        }
        logger.info("user get from cahce: {}", user.toString());
      } catch (Exception e) {
        logger.error(e.getMessage(), e);
        sessionManager.rollbackSession();
      }
      return Optional.empty();
    }
  }
}
