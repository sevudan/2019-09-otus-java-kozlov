package ru.otus.hw12.jetty.services;

import org.eclipse.jetty.security.AbstractLoginService;
import org.eclipse.jetty.util.security.Password;
import ru.otus.hw12.jetty.services.dbservice.DbServiceUser;
import ru.otus.hw12.model.User;

import java.util.Optional;

public class HibernateLoginServiceImpl extends AbstractLoginService {

  private final DbServiceUser dbServiceUser;

  public HibernateLoginServiceImpl(DbServiceUser dbServiceUser) {
    this.dbServiceUser = dbServiceUser;
  }

  @Override
  protected String[] loadRoleInfo(UserPrincipal userPrincipal) {
    return new String[]{"admin"};
  }

  @Override
  protected UserPrincipal loadUserInfo(String login) {
    System.out.println(String.format("HibernateLoginService#loadUserInfo(%s)", login));
    Optional<User> dbUser = dbServiceUser.findByLogin(login);
    return dbUser.map(u -> new UserPrincipal(u.getLogin(), new Password(u.getPassword()))).orElse(null);
  }
}
