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

public class OneToManyUsage {
    public static void main(String[] args) {
        StandardServiceRegistry registry = new StandardServiceRegistryBuilder().configure()
                .build();
        try {
            SessionFactory sf = new MetadataSources(registry).buildMetadata()
                    .buildSessionFactory();

            User user = new User();
            user.setLogin("login2");
            user.setPassword("password");
            Post post = new Post();
            post.setUser(user);
            post.setText("TEXT");
            post.setPriceHistories(List.of(
                    new PriceHistory(0, 10, 20, LocalDateTime.now()),
                    new PriceHistory(0, 20, 30, LocalDateTime.now()
                            .plusDays(1))
            ));
            create(sf, user);
            create(sf, post);
            Post postFromDB = sf.openSession().createQuery(
                    "FROM Post WHERE id = :fId", Post.class)
                    .setParameter("fId", post.getId())
                    .getSingleResult();
            postFromDB.getPriceHistories().forEach(System.out::println);
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
            if (session != null) {
                session.close();
            }
        }
    }
}
