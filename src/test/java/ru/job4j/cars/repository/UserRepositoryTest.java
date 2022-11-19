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
import ru.job4j.cars.model.User;

import java.util.List;

import static org.assertj.core.api.Assertions.*;

public class UserRepositoryTest {
    private static SessionFactory sf;
    private final CrudRepository cr = new CrudRepository(sf);

    @BeforeAll
    public static void getSessionFactory() {
        StandardServiceRegistry registry = new StandardServiceRegistryBuilder()
                .configure().build();
        sf = new MetadataSources(registry).buildMetadata().buildSessionFactory();
    }

    @AfterEach
    public void cleanTables() {
        Session session = null;
        Transaction transaction = null;
        try {
            session = sf.openSession();
            session.beginTransaction();
            session.createMutationQuery("DELETE User").executeUpdate();
            transaction = session.getTransaction();
            transaction.commit();
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
    public void whenCreateThenFindByIdReturnsUserWithId() {
        UserRepository userRepository = new UserRepository(cr);
        User user = new User();
        user.setLogin("login");
        user.setPassword("password");
        userRepository.create(user);
        User userFromDB = userRepository.findById(user.getId()).get();
        assertThat(userFromDB.getId()).isNotEqualTo(0);
        assertThat(userFromDB.getLogin()).isEqualTo(user.getLogin());
        assertThat(userFromDB.getPassword()).isEqualTo(user.getPassword());
    }

    @Test
    public void whenUpdateThenFindByIdReturnsChangedUser() {
        UserRepository userRepository = new UserRepository(cr);
        User user = new User();
        user.setLogin("login");
        user.setPassword("password");
        userRepository.create(user);
        User changedUser = new User();
        changedUser.setId(user.getId());
        changedUser.setLogin("ChangedLogin");
        changedUser.setPassword("changedPassword");
        userRepository.update(changedUser);
        User userFromDB = userRepository.findById(user.getId()).get();
        assertThat(userFromDB.getLogin()).isEqualTo(changedUser.getLogin());
        assertThat(userFromDB.getPassword()).isEqualTo(changedUser.getPassword());
    }

    @Test
    public void whenAddTwoUsersThenFindAllOrderByIdReturnsBoth() {
        UserRepository userRepository = new UserRepository(cr);
        User user1 = new User();
        user1.setLogin("FirstLogin");
        user1.setPassword("FirstPassword");
        userRepository.create(user1);
        User user2 = new User();
        user2.setLogin("SecondLogin");
        user2.setPassword("SecondPassword");
        userRepository.create(user2);
        List<User> usersFromDB = userRepository.findAllOrderById();
        assertThat(usersFromDB.get(0).getId()).isEqualTo(user1.getId());
        assertThat(usersFromDB.get(0).getLogin()).isEqualTo(user1.getLogin());
        assertThat(usersFromDB.get(0).getPassword()).isEqualTo(user1.getPassword());
        assertThat(usersFromDB.get(1).getId()).isEqualTo(user2.getId());
        assertThat(usersFromDB.get(1).getLogin()).isEqualTo(user2.getLogin());
        assertThat(usersFromDB.get(1).getPassword()).isEqualTo(user2.getPassword());
    }

    @Test
    public void whenFindByLikeLogin() {
        UserRepository userRepository = new UserRepository(cr);
        User user1 = new User();
        user1.setLogin("abc");
        user1.setPassword("password1");
        userRepository.create(user1);
        User user2 = new User();
        user2.setLogin("def");
        user2.setPassword("password2");
        userRepository.create(user2);
        User user3 = new User();
        user3.setLogin("bmd");
        user3.setPassword("password3");
        userRepository.create(user3);
        List<User> usersFromDB = userRepository.findByLikeLogin("b");
        assertThat(usersFromDB.size()).isEqualTo(2);
        assertThat(usersFromDB.get(0).getId()).isEqualTo(user1.getId());
        assertThat(usersFromDB.get(0).getLogin()).isEqualTo(user1.getLogin());
        assertThat(usersFromDB.get(0).getPassword()).isEqualTo(user1.getPassword());
        assertThat(usersFromDB.get(1).getId()).isEqualTo(user3.getId());
        assertThat(usersFromDB.get(1).getLogin()).isEqualTo(user3.getLogin());
        assertThat(usersFromDB.get(1).getPassword()).isEqualTo(user3.getPassword());
    }

    @Test
    public void whenFindByLogin() {
        UserRepository userRepository = new UserRepository(cr);
        User user1 = new User();
        user1.setLogin("login1");
        user1.setPassword("password");
        userRepository.create(user1);
        User user2 = new User();
        user2.setLogin("login2");
        user2.setPassword("password");
        userRepository.create(user2);
        User userFromDB = userRepository.findByLogin(user1.getLogin()).get();
        assertThat(userFromDB.getId()).isEqualTo(user1.getId());
        assertThat(userFromDB.getLogin()).isEqualTo(user1.getLogin());
        assertThat(userFromDB.getPassword()).isEqualTo(user1.getPassword());
    }
}