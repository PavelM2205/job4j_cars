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
import ru.job4j.cars.model.Transmission;

import java.util.Comparator;
import java.util.List;

import static org.assertj.core.api.Assertions.*;

class TransmissionRepositoryTest {
    private static SessionFactory sf;
    private final CrudRepository crudRepository = new CrudRepository(sf);
    private final TransmissionRepository store = new TransmissionRepository(crudRepository);

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
            session.createMutationQuery("DELETE Transmission").executeUpdate();
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
    public void whenCreateThenFindByIdReturnsTransmissionWithNotNullId() {
        Transmission tr = new Transmission();
        tr.setName("Механика");
        store.create(tr);
        Transmission trFromDB = store.findById(tr.getId()).get();
        assertThat(trFromDB.getId()).isNotEqualTo(0);
        assertThat(trFromDB.getName()).isEqualTo(tr.getName());
    }

    @Test
    public void whenAddTwoTransmissionThenFindAllReturnsBoth() {
        Transmission tr1 = new Transmission();
        tr1.setName("Механика");
        store.create(tr1);
        Transmission tr2 = new Transmission();
        tr2.setName("Автомат");
        store.create(tr2);
        List<Transmission> trFromDB = store.findAll();
        trFromDB.sort(new Comparator<Transmission>() {
            @Override
            public int compare(Transmission o1, Transmission o2) {
                return Integer.valueOf(o1.getId()).compareTo(Integer.valueOf(o2.getId()));
            }
        });
        assertThat(trFromDB.get(0).getId()).isEqualTo(tr1.getId());
        assertThat(trFromDB.get(0).getName()).isEqualTo(tr1.getName());
        assertThat(trFromDB.get(1).getId()).isEqualTo(tr2.getId());
        assertThat(trFromDB.get(1).getName()).isEqualTo(tr2.getName());
    }

    @Test
    public void whenUpdateThenFindByIdReturnsChangedTransmission() {
        Transmission tr = new Transmission();
        tr.setName("Механика");
        store.create(tr);
        Transmission changedTr = new Transmission();
        changedTr.setName("Автомат");
        changedTr.setId(tr.getId());
        store.update(changedTr);
        Transmission trFromDB = store.findById(tr.getId()).get();
        assertThat(trFromDB.getName()).isEqualTo(changedTr.getName());
    }

    @Test
    public void whenDeleteThenFindAllReturnsEmptyList() {
        Transmission tr = new Transmission();
        tr.setName("Механика");
        store.create(tr);
        store.delete(tr.getId());
        List<Transmission> trFromDB = store.findAll();
        assertThat(trFromDB.isEmpty()).isTrue();
    }
}