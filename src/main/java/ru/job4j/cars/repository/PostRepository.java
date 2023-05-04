package ru.job4j.cars.repository;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;
import ru.job4j.cars.model.Post;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Repository
public class PostRepository {
    private static final Logger LOG = LoggerFactory.getLogger(PostRepository.class);
    private static final String FIND_ALL = "FROM Post";
    private static final String FIND_BY_ID = "FROM Post WHERE id = :fId";
    private static final String FIND_CARS_FOR_CURRENT_DAY =
            "FROM Post WHERE created BETWEEN :fFrom AND :fTo";
    private static final String FIND_POSTS_WITH_PHOTO =
            "FROM Post WHERE photo IS NOT NULL";
    private static final String FIND_POSTS_WITH_DEFINITE_MAKE_OF_CAR =
            "FROM Post JOIN FETCH car as c WHERE c.name LIKE :fName";
    private static final String FIND_POSTS_FOR_DEFINITE_USER =
            "FROM Post WHERE user.id = :fId";
    private final CrudRepository crudRepository;

    public PostRepository(CrudRepository crudRepository) {
        this.crudRepository = crudRepository;
        initializationInsertPhoto();
    }

    public Optional<Post> create(Post post) {
        Optional<Post> result = Optional.empty();
        try {
            crudRepository.run(session -> session.persist(post));
            result = Optional.of(post);
        } catch (Exception exc) {
            LOG.error("Exception when create Post: ", exc);
        }
        return result;
    }

    public void update(Post post) {
        crudRepository.run(session -> session.update(post));
    }

    public void delete(int id) {
        Post post = new Post();
        post.setId(id);
        crudRepository.run(session -> session.remove(post));
    }

    public List<Post> findAll() {
        return crudRepository.query(FIND_ALL, Post.class);
    }

    public Optional<Post> findById(int id) {
        return crudRepository.optional(FIND_BY_ID, Map.of("fId", id), Post.class);
    }

    public List<Post> findPostsForCurrentDate() {
        LocalDateTime now = LocalDateTime.now();
        LocalDateTime minus24Hours = now.minusHours(24);
        return crudRepository.query(FIND_CARS_FOR_CURRENT_DAY,
                Map.of("fTo", now, "fFrom", minus24Hours),
                Post.class);
    }

    public List<Post> findPostsWithPhoto() {
        return crudRepository.query(FIND_POSTS_WITH_PHOTO, Post.class);
    }

    public List<Post> findPostsWithDefiniteMakeOfCar(String name) {
        return crudRepository.query(FIND_POSTS_WITH_DEFINITE_MAKE_OF_CAR,
                Map.of("fName", String.format("%%%s%%", name)), Post.class);
    }

    public List<Post> findPostsForDefiniteUser(int userId) {
        return crudRepository.query(FIND_POSTS_FOR_DEFINITE_USER, Map.of("fId", userId),
                Post.class);
    }

    private void initializationInsertPhoto() {
        try {
            Post post1 = crudRepository.query(
                    "From Post as p WHERE p.car.carBrand.name = 'BMW'", Post.class).get(0);
            post1.setPhoto(Files.readAllBytes(Path.of("src/main/resources/static/BMW.webp")));
            this.update(post1);
            Post post2 = crudRepository.query(
                    "From Post as p WHERE p.car.carBrand.name = 'Toyota'", Post.class).get(0);
            post2.setPhoto(Files.readAllBytes(Path.of("src/main/resources/static/Toyota.webp")));
            this.update(post2);
            Post post3 = crudRepository.query(
                    "From Post as p WHERE p.car.carBrand.name = 'KIA'", Post.class).get(0);
            post3.setPhoto(Files.readAllBytes(Path.of("src/main/resources/static/KIA.webp")));
            this.update(post3);
        } catch (IOException exc) {
            LOG.error("Exception when insert photos: ", exc);
        }
    }
}
