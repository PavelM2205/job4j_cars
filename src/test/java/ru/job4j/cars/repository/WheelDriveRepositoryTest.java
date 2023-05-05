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
import ru.job4j.cars.model.WheelDrive;

import java.util.Comparator;
import java.util.List;

import static org.assertj.core.api.Assertions.*;

class WheelDriveRepositoryTest {
    private static SessionFactory sf;
    private final CrudRepository crudRepository = new CrudRepository(sf);
    private final WheelDriveRepository store = new WheelDriveRepository(crudRepository);

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
            session.createMutationQuery("DELETE WheelDrive").executeUpdate();
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
    public void whenCreateThenFindByIdReturnsWheelDriveWithNotNullId() {
        WheelDrive wd = new WheelDrive();
        wd.setName("Передний");
        store.create(wd);
        WheelDrive wdFromDB = store.findById(wd.getId()).get();
        assertThat(wdFromDB.getId()).isNotEqualTo(0);
        assertThat(wdFromDB.getName()).isEqualTo(wd.getName());
    }

    @Test
    public void whenAddTwoWheelDriveThenFindAllReturnsBoth() {
        WheelDrive wd1 = new WheelDrive();
        wd1.setName("Передний");
        store.create(wd1);
        WheelDrive wd2 = new WheelDrive();
        wd2.setName("Задний");
        store.create(wd2);
        List<WheelDrive> wdFromDB = store.findAll();
        wdFromDB.sort(new Comparator<WheelDrive>() {
            @Override
            public int compare(WheelDrive o1, WheelDrive o2) {
                return Integer.valueOf(o1.getId()).compareTo(Integer.valueOf(o2.getId()));
            }
        });
        assertThat(wdFromDB.get(0).getId()).isEqualTo(wd1.getId());
        assertThat(wdFromDB.get(0).getName()).isEqualTo(wd1.getName());
        assertThat(wdFromDB.get(1).getId()).isEqualTo(wd2.getId());
        assertThat(wdFromDB.get(1).getName()).isEqualTo(wd2.getName());
    }

    @Test
    public void whenUpdateThenFindByIdReturnsChangedWheelDrive() {
        WheelDrive wd = new WheelDrive();
        wd.setName("Передний");
        store.create(wd);
        WheelDrive changedWd = new WheelDrive();
        changedWd.setName("Задний");
        changedWd.setId(wd.getId());
        store.update(changedWd);
        WheelDrive wdFromDB = store.findById(wd.getId()).get();
        assertThat(wdFromDB.getName()).isEqualTo(changedWd.getName());
    }

    @Test
    public void whenDeleteThenFindAllReturnsEmptyList() {
        WheelDrive wd = new WheelDrive();
        wd.setName("Передний");
        store.create(wd);
        store.delete(wd.getId());
        List<WheelDrive> wdFromDB = store.findAll();
        assertThat(wdFromDB.isEmpty()).isTrue();
    }
}