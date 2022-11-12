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
import ru.job4j.cars.model.*;

import java.time.LocalDateTime;
import java.util.List;

import static org.assertj.core.api.Assertions.*;

class PostRepositoryTest {
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
        Transaction transaction = null;
        Session session = null;
        try {
            session = sf.openSession();
            session.beginTransaction();
            session.createMutationQuery("DELETE Post").executeUpdate();
            session.createMutationQuery("DELETE Car").executeUpdate();
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
    public void forceClean() {
        cleanTables();
    }

    @Test
    public void forcedClean() {
        cleanTables();
    }

    @Test
    void whenFindCarsForCurrentDate() {
        PostRepository postRepository = new PostRepository(cr);
        Post post1 = new Post();
        postRepository.create(post1);
        Post post2 = new Post();
        post2.setCreated(LocalDateTime.now().minusDays(1));
        postRepository.create(post2);
        List<Post> postsFromDB = postRepository.findPostsForCurrentDate();
        Post foundPost = postRepository.findPostsForCurrentDate().get(0);
        assertThat(postsFromDB.size()).isEqualTo(1);
        assertThat(foundPost.getId()).isEqualTo(post1.getId());
    }

    @Test
    public void whenFindPostsWithPhoto() {
        PostRepository postRepository = new PostRepository(cr);
        Post post1 = new Post();
        post1.setPhoto(new byte[1]);
        Post post2 = new Post();
        postRepository.create(post1);
        postRepository.create(post2);
        List<Post> postsFromDB = postRepository.findPostsWithPhoto();
        assertThat(postsFromDB.size()).isEqualTo(1);
        assertThat(postsFromDB.get(0).getId()).isEqualTo(post1.getId());
    }

    @Test
    public void whenFindPostsWithDefiniteMakeOfCar() {
        PostRepository postRepository = new PostRepository(cr);
        CarRepository carRepository = new CarRepository(cr);
        Car car1 = new Car();
        car1.setName("Toyota Land Cruiser");
        Car car2 = new Car();
        car2.setName("Prius toyota");
        Car car3 = new Car();
        car3.setName("Dodge");
        carRepository.create(car1);
        carRepository.create(car2);
        carRepository.create(car3);
        Post post1 = new Post();
        post1.setCar(car1);
        Post post2 = new Post();
        post2.setCar(car2);
        Post post3 = new Post();
        post3.setCar(car3);
        postRepository.create(post1);
        postRepository.create(post2);
        postRepository.create(post3);
        List<Post> postsFromDB = postRepository
                .findPostsWithDefiniteMakeOfCar("Toyota");
        assertThat(postsFromDB.size()).isEqualTo(1);
        assertThat(postsFromDB.get(0).getId()).isEqualTo(post1.getId());
    }
}