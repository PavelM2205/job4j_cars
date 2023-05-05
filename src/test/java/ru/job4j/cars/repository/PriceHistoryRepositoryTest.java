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
import ru.job4j.cars.model.PriceHistory;

import java.time.LocalDateTime;
import java.util.List;

import static org.assertj.core.api.Assertions.*;

class PriceHistoryRepositoryTest {
    private static SessionFactory sf;
    private final CrudRepository crudRepository = new CrudRepository(sf);
    PriceHistoryRepository store = new PriceHistoryRepository(crudRepository);

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
            session.createMutationQuery("DELETE PriceHistory").executeUpdate();
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
    public void whenCreateThenFindByIdReturnsPriceHistoryWithNotNullId() {
        PriceHistory ph = new PriceHistory();
        ph.setBefore(1);
        ph.setAfter(2);
        ph.setCreated(LocalDateTime.now());
        store.create(ph);
        PriceHistory phFromDB = store.findById(ph.getId()).get();
        assertThat(phFromDB.getId()).isNotEqualTo(0);
        assertThat(phFromDB.getBefore()).isEqualTo(ph.getBefore());
        assertThat(phFromDB.getAfter()).isEqualTo(ph.getAfter());
        assertThat(phFromDB.getCreated()).isEqualToIgnoringNanos(ph.getCreated());
    }

    @Test
    public void whenAddTwoPriceHistoryThenFindAllReturnsBoth() {
        PriceHistory ph1 = new PriceHistory();
        ph1.setBefore(1);
        ph1.setAfter(2);
        ph1.setCreated(LocalDateTime.now());
        store.create(ph1);
        PriceHistory ph2 = new PriceHistory();
        ph2.setBefore(3);
        ph2.setAfter(4);
        ph2.setCreated(LocalDateTime.now().plusHours(1));
        store.create(ph2);
        List<PriceHistory> phFromDB = store.findAll();
        assertThat(phFromDB.get(0).getId()).isEqualTo(ph1.getId());
        assertThat(phFromDB.get(0).getBefore()).isEqualTo(ph1.getBefore());
        assertThat(phFromDB.get(0).getAfter()).isEqualTo(ph1.getAfter());
        assertThat(phFromDB.get(0).getCreated()).isEqualToIgnoringNanos(ph1.getCreated());
        assertThat(phFromDB.get(1).getId()).isEqualTo(ph2.getId());
        assertThat(phFromDB.get(1).getBefore()).isEqualTo(ph2.getBefore());
        assertThat(phFromDB.get(1).getAfter()).isEqualTo(ph2.getAfter());
        assertThat(phFromDB.get(1).getCreated()).isEqualToIgnoringNanos(ph2.getCreated());
    }

    @Test
    public void whenUpdateThenFindByIdReturnsChangedPriceHistory() {
        PriceHistory ph = new PriceHistory();
        ph.setBefore(1);
        ph.setAfter(2);
        ph.setCreated(LocalDateTime.now());
        store.create(ph);
        PriceHistory changedPh = new PriceHistory();
        changedPh.setBefore(3);
        changedPh.setAfter(4);
        changedPh.setCreated(LocalDateTime.now().plusHours(1));
        changedPh.setId(ph.getId());
        store.update(changedPh);
        PriceHistory phFromDB = store.findById(ph.getId()).get();
        assertThat(phFromDB.getId()).isEqualTo(changedPh.getId());
        assertThat(phFromDB.getBefore()).isEqualTo(changedPh.getBefore());
        assertThat(phFromDB.getAfter()).isEqualTo(changedPh.getAfter());
        assertThat(phFromDB.getCreated()).isEqualToIgnoringNanos(changedPh.getCreated());
    }

    @Test
    public void whenDeleteThenFindAllReturnsEmptyList() {
        PriceHistory ph = new PriceHistory();
        ph.setBefore(1);
        ph.setAfter(2);
        ph.setCreated(LocalDateTime.now());
        store.create(ph);
        store.delete(ph.getId());
        List<PriceHistory> phFromDB = store.findAll();
        assertThat(phFromDB.isEmpty()).isTrue();
    }
}