package ru.otus;

import java.math.BigDecimal;
import javax.sql.DataSource;

import ru.otus.api.dao.AccauntDao;
import ru.otus.api.dao.UserDao;
import ru.otus.h2.CreateTableAccaunt;
import ru.otus.h2.CreateTableUser;
import ru.otus.jdbc.dao.AccauntDaoJdbc;
import ru.otus.jdbc.dao.UserDaoJdbc;
import ru.otus.api.service.DBServiceUser;
import ru.otus.api.service.DbServiceUserImpl;
import ru.otus.api.service.DbServiceAccauntImpl;
import ru.otus.api.service.DBServiceAccaunt;
import ru.otus.h2.DataSourceH2;
import ru.otus.api.model.User;

import ru.otus.api.model.Accaunt;
import ru.otus.jdbc.sessionmanager.SessionManagerJdbc;
import ru.otus.orm.CreateTable;

public class DbServiceDemo {

  public static void main(String[] args) throws Exception {
    DataSource dataSource = new DataSourceH2();

    CreateTable createTableUser = new CreateTableUser();
    CreateTable createTableAccaunt = new CreateTableAccaunt();
    createTableUser.create(dataSource);
    createTableAccaunt.create(dataSource);

    SessionManagerJdbc sessionManager = new SessionManagerJdbc(dataSource);

    UserDao userDao = new UserDaoJdbc(sessionManager);
    AccauntDao accauntDao = new AccauntDaoJdbc(sessionManager);

    DBServiceUser dbServiceUser = new DbServiceUserImpl(userDao);
    DBServiceAccaunt dbServiceAccaunt = new DbServiceAccauntImpl(accauntDao);

    var userRoot = new User();
    userRoot.setName("root");
    userRoot.setAge(22);

    var userFoo = new User();
    userFoo.setName("Foo");
    userFoo.setAge(15);

    var testAccaunt = new Accaunt();
    testAccaunt.setType("customer");
    testAccaunt.setRest(new BigDecimal(755.21D));


    long userRootId = dbServiceUser.saveUser(userRoot);
    dbServiceUser.getUser(userRootId);

    long userFooId = dbServiceUser.saveUser(userFoo);
    dbServiceUser.getUser(userFooId);

    long accauntId = dbServiceAccaunt.saveAccaunt(testAccaunt);
    dbServiceAccaunt.getAccaunt(accauntId);

    userFoo.setName("TST");
    dbServiceUser.saveUser(userFoo);
    dbServiceUser.getUser(userFooId);

  }
}
