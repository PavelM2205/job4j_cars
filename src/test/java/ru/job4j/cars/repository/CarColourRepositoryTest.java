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
import ru.job4j.cars.model.CarColour;

import java.util.Comparator;
import java.util.List;

import static org.assertj.core.api.Assertions.*;

class CarColourRepositoryTest {
    private static SessionFactory sf;
    private final CrudRepository crudRepository = new CrudRepository(sf);
    CarColourRepository store = new CarColourRepository(crudRepository);

    @BeforeAll
    public static void getSessionFactory() {
        StandardServiceRegistry registry = new StandardServiceRegistryBuilder()
                .configure().build();
        sf = new MetadataSources(registry).buildMetadata().buildSessionFactory();
    }

    @BeforeAll
    public static void cleanTablesBefore() {
        Session session = null;
        Transaction transaction = null;
        try {
            session = sf.openSession();
            session.beginTransaction();
            session.createMutationQuery("DELETE CarColour").executeUpdate();
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
    public void cleanTablesAfter() {
        cleanTablesBefore();
    }

    @Test
    public void whenCreateThenFindByIdReturnsCarColourWithNotNullId() {
        CarColour carColour = new CarColour();
        carColour.setName("Черный");
        store.create(carColour);
        CarColour carColourFromDB = store.findById(carColour.getId()).get();
        assertThat(carColourFromDB.getId()).isNotEqualTo(0);
        assertThat(carColourFromDB.getName()).isEqualTo(carColour.getName());
    }

    @Test
    public void whenAddTwoCarColourThenFindAllReturnsBoth() {
        CarColour carColour1 = new CarColour();
        carColour1.setName("Черный");
        CarColour carColour2 = new CarColour();
        carColour2.setName("Белый");
        store.create(carColour1);
        store.create(carColour2);
        List<CarColour> carColoursFromDB = store.findAll();
        carColoursFromDB.sort(new Comparator<CarColour>() {
            @Override
            public int compare(CarColour o1, CarColour o2) {
                return Integer.valueOf(o1.getId()).compareTo(Integer.valueOf(o2.getId()));
            }
        });
        assertThat(carColoursFromDB.get(0).getId()).isEqualTo(carColour1.getId());
        assertThat(carColoursFromDB.get(0).getName()).isEqualTo(carColour1.getName());
        assertThat(carColoursFromDB.get(1).getId()).isEqualTo(carColour2.getId());
        assertThat(carColoursFromDB.get(1).getName()).isEqualTo(carColour2.getName());
    }

    @Test
    public void whenUpdateThenFindByIdReturnsChangedCarColour() {
        CarColour carColour = new CarColour();
        carColour.setName("Черный");
        store.create(carColour);
        CarColour changedCarColour = new CarColour();
        changedCarColour.setName("Белый");
        changedCarColour.setId(carColour.getId());
        store.update(changedCarColour);
        CarColour carColourFromDB = store.findById(carColour.getId()).get();
        assertThat(carColourFromDB.getName()).isEqualTo(changedCarColour.getName());
    }

    @Test
    public void whenDeleteThenFindAllReturnsEmptyList() {
        CarColour carColour = new CarColour();
        carColour.setName("Черный");
        store.create(carColour);
        store.delete(carColour.getId());
        List<CarColour> carColoursFromDB = store.findAll();
        assertThat(carColoursFromDB.isEmpty()).isTrue();
    }
}