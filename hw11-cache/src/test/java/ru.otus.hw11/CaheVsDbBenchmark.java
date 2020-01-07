package ru.otus.hw11;

import org.hibernate.SessionFactory;
import org.openjdk.jmh.annotations.*;
import org.openjdk.jmh.runner.Runner;
import org.openjdk.jmh.runner.RunnerException;
import org.openjdk.jmh.runner.options.Options;
import org.openjdk.jmh.runner.options.OptionsBuilder;
import ru.otus.hw11.api.model.AddressDataSet;
import ru.otus.hw11.api.model.PhoneDataSet;
import ru.otus.hw11.api.model.User;
import ru.otus.hw11.api.service.DBServiceUser;
import ru.otus.hw11.api.service.DbServiceUserImpl;
import ru.otus.hw11.api.service.DbServiceUserWithCacheImpl;
import ru.otus.hw11.cache.HwCache;
import ru.otus.hw11.cache.HwCacheImpl;
import ru.otus.hw11.hibernate.HibernateUtils;
import ru.otus.hw11.hibernate.dao.UserDaoHibernate;
import ru.otus.hw11.hibernate.sessionmanager.SessionManagerHibernate;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

@State(value = Scope.Thread)
@BenchmarkMode(Mode.SingleShotTime)
@OutputTimeUnit(TimeUnit.MILLISECONDS)
@Fork(value = 2, jvmArgs = {"-Xms128m", "-Xmx128m"})
public class CaheVsDbBenchmark {

  private HwCache<String, User> cache = new HwCacheImpl<>();
  private List<Long> listUserId  = new ArrayList<>();
  private DBServiceUser dbServiceUserWithCache;
  private DBServiceUser dbServiceUser;

  public static void main(String[] args) throws RunnerException {

    Options opt = new OptionsBuilder()
            .include(CaheVsDbBenchmark.class.getSimpleName())
            .forks(1)
            .build();

    new Runner(opt).run();
  }

  @Setup
  public void setup() {
    Class[] annotateClasses = {User.class, AddressDataSet.class, PhoneDataSet.class};
    SessionFactory sessionFactory = HibernateUtils.buildSessionFactory("hibernate.cfg.xml", annotateClasses);
    var sessionManagerHibernate = new SessionManagerHibernate(sessionFactory);
    var userDaoHibernate = new UserDaoHibernate(sessionManagerHibernate);
    dbServiceUserWithCache = new DbServiceUserWithCacheImpl(userDaoHibernate, cache);
    dbServiceUser = new DbServiceUserImpl(userDaoHibernate);
    saveUsers();
  }

  @Benchmark
  public void getFromCache() throws InterruptedException{
    listUserId.stream().forEach(id -> System.out.println(dbServiceUserWithCache.getUser(id)));
  }

  @Benchmark
  public void getFromDb() {
    listUserId.stream().forEach(id -> System.out.println(dbServiceUser.getUser(id)));
  }

  private void saveUsers(){
    int size = 10;
    for (int idx = 1; idx <= size; idx++) {
      String id = String.valueOf(idx);
      User testUser = new User("Foo#" + id, 999);
      long userId = dbServiceUserWithCache.saveUser(testUser);
      listUserId.add(userId);
    }
  }
}

