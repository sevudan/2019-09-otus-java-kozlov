package ru.otus.hw11;

import org.hibernate.SessionFactory;
import org.junit.jupiter.api.*;
import static org.junit.jupiter.api.Assertions.*;

import ru.otus.hw11.api.model.AddressDataSet;
import ru.otus.hw11.api.model.PhoneDataSet;
import ru.otus.hw11.api.model.User;
import ru.otus.hw11.api.service.DBServiceUser;
import ru.otus.hw11.api.service.DbServiceUserImpl;
import ru.otus.hw11.cache.HwCacheImpl;
import ru.otus.hw11.hibernate.HibernateUtils;
import ru.otus.hw11.hibernate.dao.UserDaoHibernate;
import ru.otus.hw11.hibernate.sessionmanager.SessionManagerHibernate;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.IntStream;

/*
* -Xms64m
* -Xmx64m
*/

public class CahceTest {

  private HwCacheImpl<String, User> cache = new HwCacheImpl<>();
  private List<User> listUsers  = new ArrayList<>();
  private int size = 10;

  @BeforeEach
  public void createUsers(){
    for (int idx = 1; idx < size; idx++) {
      String id = String.valueOf(idx);
      User testUser = new User("Foo#" + id, 999);
      listUsers.add(testUser);
    }
  }

  @Test
  @DisplayName("Test add the test users into a cahce")
  public void addUser() {
    IntStream.range(1,size).forEach(id ->cache.put(String.valueOf(id), listUsers.get(id-1)) );
    assertTrue(!cache.isEmpty());
  }

  @Test
  @DisplayName("Test get the test users from a cahce")
  public void getUser() {
    IntStream.range(1,size).forEach(id ->cache.put(String.valueOf(id), listUsers.get(id-1)));
    for (int idx = 1; idx < size; idx++) {
      User user = cache.get(String.valueOf(idx));
      assertEquals(user, listUsers.get(idx-1));
    }
  }

  @Test
  @DisplayName("Test clear a cache after start GC")
  public void testWithGc() {
    IntStream.range(1,size).forEach(id ->cache.put(String.valueOf(id), listUsers.get(id-1)) );
    IntStream.range(1,size).forEach(id ->cache.get(String.valueOf(id)));

    assertTrue(!cache.isEmpty());
    System.gc();
    try {
      Thread.sleep(100);
    } catch (InterruptedException ex) {
      ex.printStackTrace();
    }
    assertTrue(cache.isEmpty());
  }

  @Test
  @DisplayName("Test clearing a cache due out of memory")
  public void testOutofMemCahce() {
    int localSize = 200000;
    IntStream.range(1,localSize).forEach(id ->cache.put(String.valueOf(id), new User("Foo", 999)) );

    IntStream.range(1,size).forEach(id ->cache.get(String.valueOf(id)));
    assertTrue(cache.cacheSize() < localSize);
  }

}