package ru.otus.jdbc.dao;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.otus.api.dao.AccauntDao;
import ru.otus.api.dao.DaoException;
import ru.otus.api.model.Accaunt;
import ru.otus.api.sessionmanager.SessionManager;
import ru.otus.jdbc.DbExecutor;
import ru.otus.jdbc.sessionmanager.SessionManagerJdbc;

import java.lang.reflect.InvocationTargetException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Optional;

public class AccauntDaoJdbc implements AccauntDao{

  private static Logger logger = LoggerFactory.getLogger(AccauntDaoJdbc.class);

  private final SessionManagerJdbc sessionManager;

  public AccauntDaoJdbc(SessionManagerJdbc sessionManager) {
    this.sessionManager = sessionManager;
  }

  @Override
  public Optional<Accaunt> findById(long id) {
    try {
      DbExecutor<Accaunt> dbExecutor = new DbExecutor<>(getConnection());
      return dbExecutor.load(id, Accaunt.class);
    } catch (SQLException | NoSuchMethodException
      | InstantiationException | IllegalAccessException
      | InvocationTargetException ex) {
    logger.error(ex.getMessage(), ex);
    }
    return Optional.empty();
  }

  @Override
  public long saveAccaunt(Accaunt accaunt) {
    try {
      DbExecutor<Accaunt> dbExecutor = new DbExecutor<>(getConnection());
      dbExecutor.createOrUpdate(accaunt);
    } catch (Exception ex) {
      logger.error(ex.getMessage(), ex);
      throw new DaoException(ex);
    }
    return accaunt.getNo();
  }

  @Override
  public SessionManager getSessionManager() {
    return sessionManager;
  }
  private Connection getConnection() {
    return sessionManager.getCurrentSession().getConnection();
  }
 }
