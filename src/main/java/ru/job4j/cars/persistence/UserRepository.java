package ru.job4j.cars.persistence;

import lombok.AllArgsConstructor;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.query.Query;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.job4j.cars.model.User;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@AllArgsConstructor
public class UserRepository {
    private static final Logger LOG = LoggerFactory.getLogger(UserRepository.class);
    private final SessionFactory sf;

    public User create(User user) {
        Transaction transaction = null;
        try (Session session = sf.openSession()) {
            transaction = session.beginTransaction();
            session.createMutationQuery(
                    "INSERT INTO User (login, password) VALUES (:fLogin, :fPassword)")
                    .setParameter("fLogin", user.getLogin())
                    .setParameter("fPassword", user.getPassword())
                    .executeUpdate();
            transaction.commit();
        } catch (Exception exc) {
            if (transaction != null) {
                transaction.rollback();
            }
            LOG.error("Exception: ", exc);
        }
        user.setId(findByLogin(user.getLogin()).get().getId());
        return user;
    }

    public void update(User user) {
        Transaction transaction = null;
        try (Session session = sf.openSession()) {
            transaction = session.beginTransaction();
            session.createMutationQuery(
                    "UPDATE User SET login = :fLogin, password = :fPassword WHERE id = :fId")
                    .setParameter("fLogin", user.getLogin())
                    .setParameter("fPassword", user.getPassword())
                    .setParameter("fId", user.getId())
                    .executeUpdate();
            transaction.commit();
        } catch (Exception exc) {
            if (transaction != null) {
                transaction.rollback();
            }
            LOG.error("Exception: ", exc);
        }
    }

    public void delete(int id) {
        Transaction transaction = null;
        try (Session session = sf.openSession()) {
            transaction = session.beginTransaction();
            session.createMutationQuery(
                    "DELETE User WHERE id = :fId")
                            .setParameter("fId", id)
                            .executeUpdate();
            transaction.commit();
        } catch (Exception exc) {
            if (transaction != null) {
                transaction.rollback();
            }
            LOG.error("Exception: ", exc);
        }
    }

    public List<User> findAllOrderById() {
        List<User> result = new ArrayList<>();
        try (Session session = sf.openSession()) {
            result = session.createQuery("from User", User.class).list();
        }
        return result;
    }

    public Optional<User> findById(int id) {
        Optional<User> result = Optional.empty();
        try (Session session = sf.openSession()) {
            Query<User> query = session.createQuery(
                    "from User as u WHERE u.id = :fId", User.class)
                    .setParameter("fId", id);
            User user = query.uniqueResult();
            result = Optional.of(user);
        }
        return result;
    }

    public List<User> findByLikeLogin(String key) {
        List<User> result = new ArrayList<>();
        try (Session session = sf.openSession()) {
            result = session.createQuery(
                    "from User as u WHERE u.login LIKE :fLike", User.class)
                    .setParameter("fLike", String.format("%%%s%%", key)).list();
        }
        return result;
    }

    public Optional<User> findByLogin(String login) {
        Optional<User> result = Optional.empty();
        try (Session session = sf.openSession()) {
            Query<User> query = session.createQuery(
                    "from User as u WHERE u.login = :fLogin", User.class)
                    .setParameter("fLogin", login);
            User user = query.uniqueResult();
            result = Optional.of(user);
        }
        return result;
    }
}
