package ru.otus.hw10.dao;


import org.hibernate.Session;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.otus.hw10.api.dao.DaoException;
import ru.otus.hw10.api.dao.UserDao;
import ru.otus.hw10.api.model.User;
import ru.otus.hw10.api.sessionmanager.SessionManager;
import ru.otus.hw10.hibernate.sessionmanager.DatabaseSessionHibernate;
import ru.otus.hw10.hibernate.sessionmanager.SessionManagerHibernate;

import java.util.Optional;
public class UserDaoHibernate implements UserDao {
  private static Logger logger = LoggerFactory.getLogger(UserDaoHibernate.class);

  private final SessionManagerHibernate sessionManager;

  public UserDaoHibernate(SessionManagerHibernate sessionManager) {
    this.sessionManager = sessionManager;
  }


  @Override
  public Optional<User> findById(long id) {
    DatabaseSessionHibernate currentSession = sessionManager.getCurrentSession();
    try {
      return Optional.ofNullable(currentSession.getHibernateSession().load(User.class, id));
    } catch (Exception e) {
      logger.error(e.getMessage(), e);
    }
    return Optional.empty();
  }


  @Override
  public long saveUser(User user) {
    DatabaseSessionHibernate currentSession = sessionManager.getCurrentSession();
    try {
      Session hibernateSession = currentSession.getHibernateSession();
      if (user.getId() > 0) {
        hibernateSession.merge(user);
        hibernateSession.flush();
      } else {
        hibernateSession.persist(user);
        hibernateSession.flush();
      }
      return user.getId();
    } catch (Exception e) {
      logger.error(e.getMessage(), e);
      throw new DaoException(e);
    }
  }

  @Override
  public SessionManager getSessionManager() {
    return sessionManager;
  }
}