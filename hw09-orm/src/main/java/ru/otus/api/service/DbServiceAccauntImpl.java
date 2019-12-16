package ru.otus.api.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.otus.api.dao.AccauntDao;
import ru.otus.api.model.Accaunt;
import ru.otus.api.sessionmanager.SessionManager;

import java.util.Optional;

public class DbServiceAccauntImpl implements DBServiceAccaunt {
  private static Logger logger = LoggerFactory.getLogger(DbServiceAccauntImpl.class);

  private final AccauntDao accauntDao;

  public DbServiceAccauntImpl(AccauntDao accauntDao) {
        this.accauntDao = accauntDao;
    }

  @Override
  public long saveAccaunt(Accaunt accaunt) {
    try (SessionManager sessionManager = accauntDao.getSessionManager()) {
      sessionManager.beginSession();
      try {
        long accauntId = accauntDao.saveAccaunt(accaunt);
        sessionManager.commitSession();
        logger.info("created accaunt: {}", accauntId);
        return accauntId;
      } catch (Exception ex) {
        logger.error(ex.getMessage(), ex);
        sessionManager.rollbackSession();
        throw new DbServiceException(ex);
        }
      }
    }

    @Override
    public Optional<Accaunt> getAccaunt(long id) {
      try (SessionManager sessionManager = accauntDao.getSessionManager()) {
        sessionManager.beginSession();
        try {
          Optional<Accaunt> accauntOptional = accauntDao.findById(id);
          logger.info("accaunt: {}", accauntOptional.orElse(null));
          return accauntOptional;
        } catch (Exception ex) {
        logger.error(ex.getMessage(), ex);
        sessionManager.rollbackSession();
      }
      return Optional.empty();
    }
  }
}
