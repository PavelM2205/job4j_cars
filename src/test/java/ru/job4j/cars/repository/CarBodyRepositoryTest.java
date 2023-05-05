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
import ru.job4j.cars.model.CarBody;

import java.util.List;

import static org.assertj.core.api.Assertions.*;

class CarBodyRepositoryTest {
    private static SessionFactory sf;
    private final CrudRepository crudRepository = new CrudRepository(sf);
    private final CarBodyRepository store = new CarBodyRepository(crudRepository);

    @BeforeAll
    public static void getSessionFactory() {
        StandardServiceRegistry registry = new StandardServiceRegistryBuilder()
                .configure().build();
        sf = new MetadataSources().buildMetadata(registry).buildSessionFactory();
    }

    @BeforeAll
    public static void cleanTablesBefore() {
        Transaction transaction = null;
        Session session = null;
        try {
            session = sf.openSession();
            session.beginTransaction();
            session.createMutationQuery("DELETE CarBody").executeUpdate();
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

    @AfterEach
    public void cleanTables() {
        cleanTablesBefore();
    }

    @Test
    public void whenCreateThenFindByIdReturnsCarBodyWithNotNullId() {
        CarBody carBody1 = new CarBody();
        carBody1.setName("Седан");
        store.create(carBody1);
        CarBody carBodyFromDB = store.findById(carBody1.getId()).get();
        assertThat(carBodyFromDB.getId()).isNotEqualTo(0);
        assertThat(carBodyFromDB.getName()).isEqualTo(carBody1.getName());
    }

    @Test
    public void whenAddTwoCarBodyThenFindAllReturnsBoth() {
        CarBody carBody1 = new CarBody();
        carBody1.setName("Седан");
        CarBody carBody2 = new CarBody();
        carBody2.setName("Универсал");
        store.create(carBody1);
        store.create(carBody2);
        List<CarBody> carBodiesFromDB = store.findAll();
        assertThat(carBodiesFromDB.get(0).getId()).isEqualTo(carBody1.getId());
        assertThat(carBodiesFromDB.get(0).getName()).isEqualTo(carBody1.getName());
        assertThat(carBodiesFromDB.get(1).getName()).isEqualTo(carBody2.getName());
        assertThat(carBodiesFromDB.get(1).getId()).isEqualTo(carBody2.getId());
    }

    @Test
    public void whenUpdateThenFindByIdReturnsChangedCarBody() {
        CarBody carBody = new CarBody();
        carBody.setName("Седан");
        store.create(carBody);
        CarBody changedCarBody = new CarBody();
        changedCarBody.setName("Универсал");
        changedCarBody.setId(carBody.getId());
        store.update(changedCarBody);
        CarBody carBodyFromDB = store.findById(carBody.getId()).get();
        assertThat(carBodyFromDB.getName()).isEqualTo(changedCarBody.getName());
    }

    @Test
    public void whenDeleteThenFindAllReturnsEmptyList() {
        CarBody carBody = new CarBody();
        carBody.setName("Седан");
        store.create(carBody);
        store.delete(carBody.getId());
        List<CarBody> carBodiesFromDB = store.findAll();
        assertThat(carBodiesFromDB.isEmpty()).isTrue();
    }
}