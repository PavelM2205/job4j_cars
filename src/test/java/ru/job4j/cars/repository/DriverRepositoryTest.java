package ru.job4j.cars.repository;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import ru.job4j.cars.model.Driver;
import ru.job4j.cars.model.User;

import java.util.List;

import static org.assertj.core.api.Assertions.*;

class DriverRepositoryTest {
    private static SessionFactory sf;
    private final CrudRepository cr = new CrudRepository(sf);

    @BeforeAll
    public static void getSessionFactory() {
        StandardServiceRegistry registry = new StandardServiceRegistryBuilder()
                .configure().build();
        sf = new MetadataSources(registry).buildMetadata().buildSessionFactory();
    }

    @AfterEach
    public void clearTables() {
        Transaction transaction = null;
        Session session = null;
        try {
            session = sf.openSession();
            transaction = session.beginTransaction();
            session.createMutationQuery("DELETE Driver").executeUpdate();
            session.createMutationQuery("DELETE User").executeUpdate();
            session.getTransaction().commit();
        } catch (Exception exc) {
            if (transaction != null) {
                transaction.rollback();
            }
            throw exc;
        } finally {
            if (session != null) {
                session.close();
            }
        }
    }

    @Test
    void whenCreateThenFindByIdReturnsDriverWithNotNullId() {
        DriverRepository driverRepository = new DriverRepository(cr);
        UserRepository userRepository = new UserRepository(cr);
        User user = new User();
        user.setLogin("login");
        user.setPassword("password");
        userRepository.create(user);
        Driver driver = new Driver();
        driver.setName("Ivan");
        driver.setUser(user);
        driverRepository.create(driver);
        Driver driverFromDB = driverRepository.findById(driver.getId()).get();
        assertThat(driverFromDB.getId()).isNotEqualTo(0);
        assertThat(driverFromDB.getName()).isEqualTo(driver.getName());
        assertThat(driverFromDB.getUser().getId()).isEqualTo(user.getId());
        assertThat(driverFromDB.getUser().getLogin()).isEqualTo(user.getLogin());
        assertThat(driverFromDB.getUser().getPassword()).isEqualTo(user.getPassword());
    }

    @Test
    public void whenUpdateThenFindByIdReturnsChangedDriver() {
        DriverRepository driverRepository = new DriverRepository(cr);
        UserRepository userRepository = new UserRepository(cr);
        User user1 = new User();
        user1.setLogin("login");
        user1.setPassword("password");
        userRepository.create(user1);
        User user2 = new User();
        user2.setLogin("login2");
        user2.setPassword("password2");
        userRepository.create(user2);
        Driver driver = new Driver();
        driver.setName("Ivan");
        driver.setUser(user1);
        driverRepository.create(driver);
        Driver changedDriver = new Driver();
        changedDriver.setId(driver.getId());
        changedDriver.setName("Changed");
        changedDriver.setUser(user2);
        driverRepository.update(changedDriver);
        Driver driverFromDB = driverRepository.findById(driver.getId()).get();
        assertThat(driverFromDB.getName()).isEqualTo(changedDriver.getName());
        assertThat(driverFromDB.getUser().getId()).isEqualTo(user2.getId());
        assertThat(driverFromDB.getUser().getLogin()).isEqualTo(user2.getLogin());
        assertThat(driverFromDB.getUser().getPassword()).isEqualTo(user2.getPassword());
    }

    @Test
    public void whenAddTwoDriversThenFindAllReturnsBoth() {
        DriverRepository driverRepository = new DriverRepository(cr);
        UserRepository userRepository = new UserRepository(cr);
        User user1 = new User();
        user1.setLogin("login");
        user1.setPassword("password");
        User user2 = new User();
        user2.setLogin("login2");
        user2.setPassword("password2");
        userRepository.create(user1);
        userRepository.create(user2);
        Driver driver1 = new Driver();
        driver1.setName("Driver1");
        driver1.setUser(user1);
        Driver driver2 = new Driver();
        driver2.setName("Driver2");
        driver2.setUser(user2);
        driverRepository.create(driver1);
        driverRepository.create(driver2);
        List<Driver> driversFromDB = driverRepository.findAll();
        assertThat(driversFromDB.get(0).getId()).isEqualTo(driver1.getId());
        assertThat(driversFromDB.get(0).getName()).isEqualTo(driver1.getName());
        assertThat(driversFromDB.get(0).getUser().getId()).isEqualTo(user1.getId());
        assertThat(driversFromDB.get(0).getUser().getLogin())
                .isEqualTo(user1.getLogin());
        assertThat(driversFromDB.get(0).getUser().getPassword())
                .isEqualTo(user1.getPassword());
        assertThat(driversFromDB.get(1).getId()).isEqualTo(driver2.getId());
        assertThat(driversFromDB.get(1).getName()).isEqualTo(driver2.getName());
        assertThat(driversFromDB.get(1).getUser().getId()).isEqualTo(user2.getId());
        assertThat(driversFromDB.get(1).getUser().getLogin())
                .isEqualTo(user2.getLogin());
        assertThat(driversFromDB.get(1).getUser().getPassword())
                .isEqualTo(user2.getPassword());
    }

    @Test
    public void whenDeleteThenFindAllReturnsEmptyList() {
        DriverRepository driverRepository = new DriverRepository(cr);
        UserRepository userRepository = new UserRepository(cr);
        User user = new User();
        user.setLogin("login");
        user.setPassword("password");
        userRepository.create(user);
        Driver driver = new Driver();
        driver.setName("Ivan");
        driver.setUser(user);
        driverRepository.create(driver);
        driverRepository.delete(driver.getId());
        assertThat(driverRepository.findAll().isEmpty()).isTrue();
    }
}