package ru.job4j.cars.repository;

import lombok.AllArgsConstructor;
import ru.job4j.cars.model.User;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@AllArgsConstructor
public class UserRepository {
    private static final String FIND_ALL = "FROM User";
    private static final String FIND_BY_ID =
            "FROM User WHERE id = :fId";
    private static final String FIND_BY_LIKE_LOGIN =
            "FROM User as u WHERE u.login LIKE :fLike";
    private static final String FIND_BY_LOGIN =
            "FROM User WHERE login = :fLogin";
    private final CrudRepository crudRepository;

    public Optional<User> create(User user) {
        Optional<User> result = Optional.empty();
        crudRepository.run(session -> session.persist(user));
        result = Optional.of(user);
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
}
