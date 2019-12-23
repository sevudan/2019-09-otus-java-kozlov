package hibernate;

import org.hibernate.SessionFactory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import ru.otus.hw10.api.model.AddressDataSet;
import ru.otus.hw10.api.model.PhoneDataSet;
import ru.otus.hw10.api.model.User;
import ru.otus.hw10.api.service.DBServiceUser;
import ru.otus.hw10.api.service.DbServiceUserImpl;
import ru.otus.hw10.dao.UserDaoHibernate;
import ru.otus.hw10.hibernate.HibernateUtils;
import ru.otus.hw10.hibernate.sessionmanager.SessionManagerHibernate;

import static org.assertj.core.api.Assertions.assertThat;

public class UserDaoTest{

    SessionManagerHibernate  sessionManagerHibernate;
    UserDaoHibernate userDaoHibernate;
    DBServiceUser dbServiceUser;
    PhoneDataSet phone1 = new PhoneDataSet();
    PhoneDataSet phone2 = new PhoneDataSet();
    private static Class[] annotateClasses = {User.class, AddressDataSet.class, PhoneDataSet.class};

    @BeforeEach
    public void setUp() {

        SessionFactory sessionFactory = HibernateUtils.buildSessionFactory("hibernate.cfg.xml", annotateClasses);
        sessionManagerHibernate = new SessionManagerHibernate(sessionFactory);
        userDaoHibernate = new UserDaoHibernate(sessionManagerHibernate);
        dbServiceUser = new DbServiceUserImpl(userDaoHibernate);
    }

    @DisplayName("корректно сохранять пользователя")
    @Test
    void shouldCorrectSaveUser() {

        var userRoot = new User();
        userRoot.setName("Root");
        userRoot.setAge(22);

        long id = dbServiceUser.saveUser(userRoot);
        assertThat(id).isGreaterThan(0);

        User actualUser = dbServiceUser.getUser(id).get();
        assertThat(actualUser).isNotNull().hasFieldOrPropertyWithValue("name", userRoot.getName());
        assertThat(actualUser).isNotNull().hasFieldOrPropertyWithValue("age", userRoot.getAge());
    }

    @Test
    @DisplayName("Корректно загружать юзера по ID")
    void shouldFindCorrectUserById() {

        var userFoo = new User();
        userFoo.setName("Foo");
        userFoo.setAge(18);

        long id = dbServiceUser.saveUser(userFoo);
        User testedUser = dbServiceUser.getUser(id).get();
        assertThat(testedUser.getId()).isEqualTo(1);
    }


    @DisplayName("корректно сохранять адрес пользователя")
    @Test
    void shouldCorrectSaveUserAddress() {

        var userFoo = new User();
        userFoo.setName("Foo");
        userFoo.setAge(22);
        userFoo.setAddressSet(new AddressDataSet("Zhukova str."));

        long id = dbServiceUser.saveUser(userFoo);

        User actualUser = dbServiceUser.getUser(id).get();

        assertThat(actualUser.getAddressSet()).isNotNull();
        assertThat(actualUser.getAddressSet().getStreet()).isEqualTo("Zhukova str.");
    }

    @DisplayName("корректно изменять адрес пользователя")
    @Test
    void shouldUpdateUserAddress() {

        var userFoo = new User();
        userFoo.setName("Foo");
        userFoo.setAge(22);
        userFoo.setAddressSet(new AddressDataSet("Zhukova str."));
        long id = dbServiceUser.saveUser(userFoo);
        userFoo.setAddressSet(new AddressDataSet("The Red Square"));

        dbServiceUser.saveUser(userFoo);
        User actualUser = dbServiceUser.getUser(id).get();

        assertThat(actualUser.getAddressSet()).isNotNull();
        assertThat(actualUser.getAddressSet().getStreet()).isEqualTo("The Red Square");
    }

    @DisplayName("корректно сохранять телефон пользователя")
    @Test
    void shouldCorrectSaveUserPhone() {

        var userFoo = new User();
        userFoo.setName("Foo");
        userFoo.setAge(22);
        phone1.setNumber("+7 (915) 1234567");
        phone2.setNumber("8 (499) 9876543");
        userFoo.getPhoneDataSets().add(phone1);
        userFoo.getPhoneDataSets().add(phone2);

        long id = dbServiceUser.saveUser(userFoo);
        User actualUser = dbServiceUser.getUser(id).get();

        assertThat(actualUser.getPhoneDataSets()).isNotEmpty();
        assertThat(actualUser.getPhoneDataSets())
                .extracting(PhoneDataSet::getNumber)
                .containsExactlyInAnyOrder("+7 (915) 1234567", "8 (499) 9876543");
    }

    @DisplayName("корректно изменять номер телефона пользователя")
    @Test
    void shouldUpdateSaveUserPhone() {
        var userFoo = new User();
        userFoo.setName("Foo");
        userFoo.setAge(22);
        phone1.setNumber("+7 (915) 1234567");
        phone2.setNumber("8 (499) 9876543");
        userFoo.getPhoneDataSets().add(phone1);
        long id = dbServiceUser.saveUser(userFoo);

        userFoo.getPhoneDataSets().remove(phone1);
        userFoo.getPhoneDataSets().add(phone2);
        dbServiceUser.saveUser(userFoo);
        User actualUser = dbServiceUser.getUser(id).get();

        assertThat(actualUser.getPhoneDataSets()).isNotEmpty();
        assertThat(actualUser.getPhoneDataSets())
                .extracting(PhoneDataSet::getNumber)
                .containsExactly("8 (499) 9876543");
    }
}
