package ru.job4j.cars.repository;

import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;
import ru.job4j.cars.model.Post;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@Repository
@AllArgsConstructor
public class PostRepository {
    private static final Logger LOG = LoggerFactory.getLogger(PostRepository.class);
    private static final String FIND_ALL = "FROM Post";
    private static final String FIND_BY_ID = "FROM Post WHERE id = :fId";
    private final CrudRepository crudRepository;

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
}
