package ru.job4j.cars;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import ru.job4j.cars.model.Post;
import ru.job4j.cars.model.PriceHistory;
import ru.job4j.cars.model.User;

import java.time.LocalDateTime;
import java.util.List;

public class ManyToManyUsage {
    public static void main(String[] args) {
        StandardServiceRegistry registry = new StandardServiceRegistryBuilder()
                .configure().build();
        try {
            SessionFactory sf = new MetadataSources(registry).buildMetadata()
                    .buildSessionFactory();
            User user1 = new User();
            user1.setLogin("login7");
            user1.setPassword("password");
            User user2 = new User();
            user2.setLogin("login8");
            user2.setPassword("password");
            create(sf, user1);
            create(sf, user2);
            Post post = new Post();
            post.setUser(user2);
            post.setText("Text Text");
            post.setPriceHistories(List.of(
                    new PriceHistory(0, 50, 60, LocalDateTime.now()),
                    new PriceHistory(0, 70, 80, LocalDateTime.now())
            ));
            post.setParticipants(List.of(user1));
            create(sf, post);
            sf.openSession()
                    .createQuery("FROM Post WHERE id = :fId", Post.class)
                    .setParameter("fId", post.getId())
                    .getSingleResult()
                    .getParticipants()
                    .forEach(System.out::println);
        } catch (Exception exc) {
            exc.printStackTrace();
        } finally {
            StandardServiceRegistryBuilder.destroy(registry);
        }
    }

    public static <T> void create(SessionFactory sf, T model) {
        Session session = sf.openSession();
        try {
            session.beginTransaction();
            session.persist(model);
            session.getTransaction().commit();
        } catch (Exception exc) {
            Transaction transaction = session.getTransaction();
            if (transaction.isActive()) {
                transaction.rollback();
            }
            throw exc;
        } finally {
            session.close();
        }
    }

    public static List<Post> findAll(SessionFactory sf) {
        Session session = sf.openSession();
        session.beginTransaction();
        List<Post> result = session.createQuery("FROM Post", Post.class)
                .list();
        session.getTransaction().commit();
        session.close();
        return result;
    }
}
