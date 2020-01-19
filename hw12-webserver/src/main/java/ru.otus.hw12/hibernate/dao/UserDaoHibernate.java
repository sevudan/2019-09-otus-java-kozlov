package ru.otus.hw12.hibernate.dao;


import org.hibernate.Session;
import org.hibernate.query.Query;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.otus.hw12.dao.DaoException;
import ru.otus.hw12.dao.UserDao;
import ru.otus.hw12.hibernate.sessionmanager.DatabaseSessionHibernate;
import ru.otus.hw12.hibernate.sessionmanager.SessionManager;
import ru.otus.hw12.hibernate.sessionmanager.SessionManagerHibernate;
import ru.otus.hw12.model.User;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.LongStream;

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
  public Optional<User> findByLogin(String login) {
    DatabaseSessionHibernate currentSession = sessionManager.getCurrentSession();
    try {
      Session hibernateSession = currentSession.getHibernateSession();
      Query query = hibernateSession.createQuery(String.format("SELECT id FROM User WHERE login = '%s'", login));
      List<Long> result = query.getResultList();

      if (result.isEmpty()) return Optional.empty();

      long id = result.get(0);
      logger.error("MyINFO: findByLogin, get id  {}", id);
      return findById(id);
    }catch (Exception e) {
      logger.error(e.getMessage(), e);
    }
    return Optional.empty();
  }

  @Override
  public List<User> getAllUsers() {
    DatabaseSessionHibernate currentSession = sessionManager.getCurrentSession();
    try {
      Session hibernateSession = currentSession.getHibernateSession();
      Query queryCountUsers = hibernateSession.createQuery("SELECT COUNT (id) FROM User");
      List<Long> listId = queryCountUsers.getResultList();
      long usersCount = listId.get(0)+1;

      return LongStream.range(1, usersCount)
              .mapToObj(id -> findById(id).get())
              .collect(Collectors.toList());

    }catch (Exception e) {
      logger.error(e.getMessage(), e);
    }
    return new ArrayList<>();
  }

  @Override
  public SessionManager getSessionManager() {
    return sessionManager;
  }
}
