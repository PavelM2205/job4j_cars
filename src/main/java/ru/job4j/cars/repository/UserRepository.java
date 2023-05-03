package ru.job4j.cars.repository;

import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;
import ru.job4j.cars.model.User;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@Repository
@AllArgsConstructor
public class UserRepository {
    private static final Logger LOG = LoggerFactory.getLogger(UserRepository.class);
    private static final String FIND_ALL = "FROM User";
    private static final String FIND_BY_ID =
            "FROM User WHERE id = :fId";
    private static final String FIND_BY_LIKE_LOGIN =
            "FROM User as u WHERE u.login LIKE :fLike";
    private static final String FIND_BY_LOGIN =
            "FROM User WHERE login = :fLogin";
    private static final String FIND_BY_LOGIN_AND_PASSWORD =
            "FROM User as u WHERE u.login = :fLogin AND u.password = :fPassword";
    private final CrudRepository crudRepository;

    public Optional<User> create(User user) {
        Optional<User> result = Optional.empty();
        try {
            crudRepository.run(session -> session.persist(user));
            result = Optional.of(user);
        } catch (Exception exc) {
            LOG.error("Exception when add User into DB: ", exc);
        }
        return result;
    }

    public void update(User user) {
        crudRepository.run(session -> session.update(user));
    }

    public void delete(int id) {
        User user = new User();
        user.setId(id);
        crudRepository.run(session -> session.remove(user));
    }

    public List<User> findAllOrderById() {
        return crudRepository.query(FIND_ALL, User.class);
    }

    public Optional<User> findById(int id) {
        return crudRepository.optional(FIND_BY_ID, Map.of("fId", id),
                User.class);
    }

    public List<User> findByLikeLogin(String key) {
        return crudRepository.query(FIND_BY_LIKE_LOGIN, Map.of("fLike",
                        String.format("%%%s%%", key)), User.class);
    }

    public Optional<User> findByLogin(String login) {
        return crudRepository.optional(FIND_BY_LOGIN, Map.of("fLogin", login),
                User.class);
    }

    public Optional<User> findByLoginAndPassword(String login, String password) {
        return crudRepository.optional(FIND_BY_LOGIN_AND_PASSWORD,
                Map.of("fLogin", login, "fPassword", password), User.class);
    }
}
