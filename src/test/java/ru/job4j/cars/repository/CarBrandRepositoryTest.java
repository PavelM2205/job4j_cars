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
import ru.job4j.cars.model.CarBrand;

import java.util.Comparator;
import java.util.List;

import static org.assertj.core.api.Assertions.*;

class CarBrandRepositoryTest {
    private static SessionFactory sf;
    private final CrudRepository crudRepository = new CrudRepository(sf);
    private final CarBrandRepository store = new CarBrandRepository(crudRepository);

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
            session.createMutationQuery("DELETE CarBrand").executeUpdate();
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
    public void whenCreateThenFindByIdReturnsCarBrandWithNotNullId() {
        CarBrand carBrand = new CarBrand();
        carBrand.setName("Toyota");
        store.create(carBrand);
        CarBrand carBrandFromDB = store.findById(carBrand.getId()).get();
        assertThat(carBrandFromDB.getId()).isNotEqualTo(0);
        assertThat(carBrandFromDB.getName()).isEqualTo(carBrandFromDB.getName());
    }

    @Test
    public void whenAddTwoCarBrandThenFindAllReturnsBoth() {
        CarBrand carBrand1 = new CarBrand();
        carBrand1.setName("Toyota");
        CarBrand carBrand2 = new CarBrand();
        carBrand2.setName("BMW");
        store.create(carBrand1);
        store.create(carBrand2);
        System.out.println(carBrand1);
        System.out.println(carBrand2);
        List<CarBrand> carBrandsFromDB = store.findAll();
        carBrandsFromDB.sort(new Comparator<CarBrand>() {
            @Override
            public int compare(CarBrand o1, CarBrand o2) {
                return Integer.valueOf(o1.getId()).compareTo(Integer.valueOf(o2.getId()));
            }
        });
        System.out.println(carBrandsFromDB.get(0));
        System.out.println(carBrandsFromDB.get(1));
        assertThat(carBrandsFromDB.get(0).getId()).isEqualTo(carBrand1.getId());
        assertThat(carBrandsFromDB.get(0).getName()).isEqualTo(carBrand1.getName());
        assertThat(carBrandsFromDB.get(1).getId()).isEqualTo(carBrand2.getId());
        assertThat(carBrandsFromDB.get(1).getName()).isEqualTo(carBrand2.getName());
    }

    @Test
    public void whenUpdateThenFindByIdReturnsChangedCarBrand() {
        CarBrand carBrand = new CarBrand();
        carBrand.setName("Toyota");
        store.create(carBrand);
        CarBrand changedCarBrand = new CarBrand();
        changedCarBrand.setName("BMW");
        changedCarBrand.setId(carBrand.getId());
        store.update(changedCarBrand);
        CarBrand carBrandFromDB = store.findById(carBrand.getId()).get();
        assertThat(carBrandFromDB.getName()).isEqualTo(changedCarBrand.getName());
    }

    @Test
    public void whenDeleteThenFindAllReturnsEmptyList() {
        CarBrand carBrand = new CarBrand();
        carBrand.setName("Toyota");
        store.create(carBrand);
        store.delete(carBrand.getId());
        List<CarBrand> carBrands = store.findAll();
        assertThat(carBrands.isEmpty()).isTrue();
    }
}