package ru.job4j.cars.usage;

import org.hibernate.SessionFactory;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.job4j.cars.model.User;
import ru.job4j.cars.persistence.UserRepository;

public class UserUsage {
    private static final Logger LOG = LoggerFactory.getLogger(UserUsage.class);

    public static void main(String[] args) {
        StandardServiceRegistry registry = new StandardServiceRegistryBuilder().configure()
                .build();
        try (SessionFactory sf = new MetadataSources(registry).buildMetadata()
                .buildSessionFactory()) {
            UserRepository repo = new UserRepository(sf);
            var user = new User();
            user.setLogin("admin113");
            user.setPassword("admin113");
            repo.create(user);
            System.out.println(user.getId());
            repo.findAllOrderById().forEach(System.out::println);
            repo.findByLikeLogin("e").forEach(System.out::println);
            repo.findById(user.getId()).ifPresent(System.out::println);
            repo.findByLogin("admin").ifPresent(System.out::println);
            user.setPassword("password");
            repo.update(user);
            repo.findById(user.getId()).ifPresent(System.out::println);
            repo.delete(user.getId());
            repo.findAllOrderById().forEach(System.out::println);
        } catch (Exception exc) {
            LOG.error("Exception: ", exc);
        } finally {
            StandardServiceRegistryBuilder.destroy(registry);
        }
    }
}
