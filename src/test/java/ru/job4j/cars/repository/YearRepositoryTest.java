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
import ru.job4j.cars.model.Year;

import java.util.Comparator;
import java.util.List;

import static org.assertj.core.api.Assertions.*;

class YearRepositoryTest {
    private static SessionFactory sf;
    private final CrudRepository crudRepository = new CrudRepository(sf);
    YearRepository store = new YearRepository(crudRepository);

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
            session.createMutationQuery("DELETE Year").executeUpdate();
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
    public void whenCreateThenFindByIdReturnsYearWithNotNullId() {
        Year year = new Year();
        year.setYear(2015);
        store.create(year);
        Year yearFromDB = store.findById(year.getId()).get();
        assertThat(yearFromDB.getId()).isNotEqualTo(0);
        assertThat(yearFromDB.getYear()).isEqualTo(year.getYear());
    }

    @Test
    public void whenAddTwoYearThenFindAllReturnsBoth() {
        Year year1 = new Year();
        year1.setYear(2015);
        store.create(year1);
        Year year2 = new Year();
        year2.setYear(2000);
        store.create(year2);
        List<Year> yearsFromDB = store.findAll();
        yearsFromDB.sort(new Comparator<Year>() {
            @Override
            public int compare(Year o1, Year o2) {
                return Integer.valueOf(o1.getId()).compareTo(Integer.valueOf(o2.getId()));
            }
        });
        assertThat(yearsFromDB.get(0).getId()).isEqualTo(year1.getId());
        assertThat(yearsFromDB.get(0).getYear()).isEqualTo(year1.getYear());
        assertThat(yearsFromDB.get(1).getId()).isEqualTo(year2.getId());
        assertThat(yearsFromDB.get(1).getYear()).isEqualTo(year2.getYear());
    }

    @Test
    public void whenUpdateThenFindByIdReturnsChangedYear() {
        Year year = new Year();
        year.setYear(2015);
        store.create(year);
        Year changedYear = new Year();
        changedYear.setYear(2015);
        changedYear.setId(year.getId());
        store.update(changedYear);
        Year yearFromDB = store.findById(year.getId()).get();
        assertThat(yearFromDB.getYear()).isEqualTo(changedYear.getYear());
    }

    @Test
    public void whenDeleteThenFindAllReturnsEmptyList() {
        Year year = new Year();
        year.setYear(2015);
        store.create(year);
        store.delete(year.getId());
        List<Year> yearsFromDB = store.findAll();
        assertThat(yearsFromDB.isEmpty()).isTrue();
    }
}