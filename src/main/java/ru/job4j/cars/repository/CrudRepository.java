package ru.job4j.cars.repository;

import lombok.AllArgsConstructor;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.function.Consumer;
import java.util.function.Function;

@Component
@AllArgsConstructor
public class CrudRepository {
    private final SessionFactory sf;

    public void run(Consumer<Session> command) {
        tx(session -> {
            command.accept(session);
            return null;
        });
    }

    public void run(String query, Map<String, Object> args) {
        Consumer<Session> command = session -> {
            var qr = session.createMutationQuery(query);
            for (Map.Entry<String, Object> entry : args.entrySet()) {
                qr.setParameter(entry.getKey(), entry.getValue());
            }
            qr.executeUpdate();
        };
        run(command);
    }

    public <T> List<T> query(String query, Class<T> cl) {
        Function<Session, List<T>> command = session ->
            session.createQuery(query, cl).list();
        return tx(command);
    }

    public <T> Optional<T> optional(String query, Map<String, Object> args,
                                    Class<T> cl) {
        Function<Session, Optional<T>> command = session -> {
            var qr = session.createQuery(query, cl);
            for (Map.Entry<String, Object> entry : args.entrySet()) {
                qr.setParameter(entry.getKey(), entry.getValue());
            }
            return Optional.ofNullable(qr.getSingleResultOrNull());
        };
        return tx(command);
    }

    public <T> List<T> query(String query, Map<String, Object> args, Class<T> cl) {
        Function<Session, List<T>> command = session -> {
            var qr = session.createQuery(query, cl);
            for (Map.Entry<String, Object> entry : args.entrySet()) {
                qr.setParameter(entry.getKey(), entry.getValue());
            }
            return qr.list();
        };
        return tx(command);
    }

    private <T> T tx(Function<Session, T> command) {
        Session session = sf.openSession();
        try {
            session.beginTransaction();
            T rsl = command.apply(session);
            session.getTransaction().commit();
            return rsl;
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
}
