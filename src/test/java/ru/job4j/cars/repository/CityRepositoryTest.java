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
import ru.job4j.cars.model.City;

import java.util.List;

import static org.assertj.core.api.Assertions.*;

class CityRepositoryTest {
    private static SessionFactory sf;
    private final CrudRepository crudRepository = new CrudRepository(sf);
    private final CityRepository store = new CityRepository(crudRepository);

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
            session.createMutationQuery("DELETE City").executeUpdate();
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
    public void whenCreateThenFindByIdReturnsCityWithNotNullId() {
        City city = new City();
        city.setName("Москва");
        store.create(city);
        City cityFromDB = store.findById(city.getId()).get();
        assertThat(cityFromDB.getId()).isNotEqualTo(0);
        assertThat(cityFromDB.getName()).isEqualTo(city.getName());
    }

    @Test
    public void whenAddTwoCityThenFindAllReturnsBoth() {
        City city1 = new City();
        city1.setName("Москва");
        store.create(city1);
        City city2 = new City();
        city2.setName("Новосибирск");
        store.create(city2);
        List<City> citiesFromDB = store.findAll();
        assertThat(citiesFromDB.get(0).getId()).isEqualTo(city1.getId());
        assertThat(citiesFromDB.get(0).getName()).isEqualTo(city1.getName());
        assertThat(citiesFromDB.get(1).getId()).isEqualTo(city2.getId());
        assertThat(citiesFromDB.get(1).getName()).isEqualTo(city2.getName());
    }

    @Test
    public void whenUpdateThenFindByIdReturnsChangedCity() {
        City city = new City();
        city.setName("Москва");
        store.create(city);
        City changedCity = new City();
        changedCity.setName("Красноярск");
        changedCity.setId(city.getId());
        store.update(changedCity);
        City cityFromDB = store.findById(city.getId()).get();
        assertThat(cityFromDB.getName()).isEqualTo(changedCity.getName());
    }

    @Test
    public void whenDeleteThenFindAllReturnsEmptyList() {
        City city = new City();
        city.setName("Москва");
        store.create(city);
        store.delete(city.getId());
        List<City> citiesFromDB = store.findAll();
        assertThat(citiesFromDB.isEmpty()).isTrue();
    }
}