package ru.otus.jdbc.dao;

import java.lang.reflect.InvocationTargetException;
import java.sql.Connection;
import java.sql.SQLException;

import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.otus.api.dao.UserDao;
import ru.otus.api.dao.DaoException;
import ru.otus.jdbc.DbExecutor;
import ru.otus.api.model.User;
import ru.otus.api.sessionmanager.SessionManager;
import ru.otus.jdbc.sessionmanager.SessionManagerJdbc;

public class UserDaoJdbc implements UserDao {
  private static Logger logger = LoggerFactory.getLogger(UserDaoJdbc.class);

  private final SessionManagerJdbc sessionManager;
  private final DbExecutor<User> dbExecutor;

  public UserDaoJdbc(SessionManagerJdbc sessionManager, DbExecutor<User> dbExecutor) {
    this.sessionManager = sessionManager;
    this.dbExecutor = dbExecutor;
  }

  @Override
  public Optional<User> findById(long id) {
    try {
    return dbExecutor.load(id, User.class);
    } catch (SQLException | NoSuchMethodException
            | InstantiationException | IllegalAccessException
            | InvocationTargetException e) {
       logger.error(e.getMessage(), e);
     }
    return Optional.empty();
  }


  @Override
  public long saveUser(User user) {
    try {
      dbExecutor.createOrUpdate(user);
    } catch (Exception e) {
      logger.error(e.getMessage(), e);
      throw new DaoException(e);
    }
   return 1;
  }

  @Override
  public SessionManager getSessionManager() {
    return sessionManager;
  }

  private Connection getConnection() {
    return sessionManager.getCurrentSession().getConnection();
  }
}
