package ru.otus.hw12;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.eclipse.jetty.security.LoginService;
import org.hibernate.SessionFactory;
import ru.otus.hw12.dao.UserDao;
import ru.otus.hw12.hibernate.HibernateUtils;
import ru.otus.hw12.hibernate.dao.UserDaoHibernate;
import ru.otus.hw12.hibernate.sessionmanager.SessionManagerHibernate;
import ru.otus.hw12.jetty.server.UsersWebServer;
import ru.otus.hw12.jetty.server.UsersWebServerImpl;
import ru.otus.hw12.jetty.services.*;
import ru.otus.hw12.hibernate.dbservice.DbServiceUser;
import ru.otus.hw12.hibernate.dbservice.DbServiceUserImpl;
import ru.otus.hw12.model.User;

import static ru.otus.hw12.jetty.server.SecurityType.FILTER_BASED;

/*
    Полезные для демо ссылки

    // Стартовая страница
    http://localhost:8080

    // Страница администратора
    http://localhost:8080/admin

*/

public class StartServer {
    private static final int WEB_SERVER_PORT = 8080;
    private static final String TEMPLATES_DIR = "/templates/";

  private static Class[] annotateClasses = {User.class};

  private static void createUsers(DbServiceUser dbServiceUser) {

    User admin = new User();
    admin.setLogin("admin");
    admin.setUsername("Admin");
    admin.setPassword("admin");

    dbServiceUser.saveUser(admin);
  }

  public static void main(String[] args) throws Exception {

    SessionFactory sessionFactory = HibernateUtils.buildSessionFactory("cfg/hibernate.cfg.xml", annotateClasses);
    SessionManagerHibernate sessionManager = new SessionManagerHibernate(sessionFactory);
    UserDao userDaoHibernate = new UserDaoHibernate(sessionManager);
    DbServiceUser dbServiceUser = new DbServiceUserImpl(userDaoHibernate);
    createUsers(dbServiceUser);

    UserAuthService userAuthServiceForFilterBasedSecurity = new UserAuthServiceImpl(dbServiceUser);
    LoginService loginServiceForBasicSecurity = new HibernateLoginServiceImpl(dbServiceUser);

    Gson gson = new GsonBuilder().serializeNulls()
            .excludeFieldsWithoutExposeAnnotation()
            .setPrettyPrinting()
            .create();

    TemplateProcessor templateProcessor = new TemplateProcessorImpl(TEMPLATES_DIR);

    UsersWebServer usersWebServer = new UsersWebServerImpl(WEB_SERVER_PORT,
            FILTER_BASED,
            userAuthServiceForFilterBasedSecurity,
            loginServiceForBasicSecurity,
            dbServiceUser,
            gson,
            templateProcessor);

    usersWebServer.start();
    usersWebServer.join();
    }
}