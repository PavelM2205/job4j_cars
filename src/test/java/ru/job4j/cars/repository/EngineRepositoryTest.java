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
import ru.job4j.cars.model.Engine;

import java.util.List;

import static org.assertj.core.api.Assertions.*;

public class EngineRepositoryTest {
    private static SessionFactory sf;
    private final CrudRepository cr = new CrudRepository(sf);

    @BeforeAll
    public static void getSessionFactory() {
        StandardServiceRegistry registry = new StandardServiceRegistryBuilder()
                .configure().build();
        sf = new MetadataSources(registry).buildMetadata().buildSessionFactory();
    }

    @AfterEach
    public void cleanDB() {
        Transaction transaction = null;
        Session session = null;
        try {
            session = sf.openSession();
            transaction = session.beginTransaction();
            session.createMutationQuery("DELETE Engine").executeUpdate();
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
    public void whenCreateThenFindByIdReturnsEngineWithNotNullId() {
        EngineRepository engineStore = new EngineRepository(cr);
        Engine engine = new Engine();
        engine.setName("NP 500");
        engineStore.create(engine);
        Engine engineFromDB = engineStore.findById(engine.getId()).get();
        assertThat(engineFromDB.getId()).isNotEqualTo(0);
        assertThat(engineFromDB.getName()).isEqualTo(engineFromDB.getName());
    }

    @Test
    public void whenUpdateThenMustBeChangedEngine() {
        EngineRepository engineRepository = new EngineRepository(cr);
        Engine engine = new Engine();
        engine.setName("NP 500");
        engineRepository.create(engine);
        Engine changedEngine = new Engine();
        changedEngine.setId(engine.getId());
        changedEngine.setName("DEF 800");
        engineRepository.update(changedEngine);
        Engine engineFromDB = engineRepository.findById(engine.getId()).get();
        assertThat(engineFromDB.getName()).isEqualTo(changedEngine.getName());
    }

    @Test
    public void whenAddTwoEngineThenFindAllReturnsBoth() {
        EngineRepository engineRepository = new EngineRepository(cr);
        Engine engine1 = new Engine();
        Engine engine2 = new Engine();
        engine1.setName("Engine First");
        engine2.setName("Engine Second");
        engineRepository.create(engine1);
        engineRepository.create(engine2);
        List<Engine> enginesFromDB = engineRepository.findAll();
        assertThat(enginesFromDB.size()).isEqualTo(2);
        assertThat(enginesFromDB.get(0).getId()).isEqualTo(engine1.getId());
        assertThat(enginesFromDB.get(0).getName()).isEqualTo(engine1.getName());
        assertThat(enginesFromDB.get(1).getId()).isEqualTo(engine2.getId());
        assertThat(enginesFromDB.get(1).getName()).isEqualTo(engine2.getName());
    }

    @Test
    public void whenDeleteEngineThenFindAllReturnsEmptyList() {
        EngineRepository engineRepository = new EngineRepository(cr);
        Engine engine = new Engine();
        engine.setName("NEW ENGINE");
        engineRepository.create(engine);
        engineRepository.delete(engine.getId());
        List<Engine> enginesFromDB = engineRepository.findAll();
        assertThat(enginesFromDB.isEmpty()).isTrue();
    }
}