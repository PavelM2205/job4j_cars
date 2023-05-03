package ru.job4j.cars.service;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import ru.job4j.cars.model.Post;
import ru.job4j.cars.repository.PostRepository;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

@AllArgsConstructor
@Service
public class PostService {
    private final PostRepository postRepository;

    public Post create(Post post) {
        Optional<Post> optPost = postRepository.create(post);
        if (optPost.isEmpty()) {
            throw new IllegalStateException("The Post has not added");
        }
        return optPost.get();
    }

    public void update(Post post) {
        postRepository.update(post);
    }

    public void delete(int id) {
        postRepository.delete(id);
    }

    public List<Post> findAll() {
        return postRepository.findAll();
    }

    public Post findById(int id) {
        Optional<Post> optPost = postRepository.findById(id);
        if (optPost.isEmpty()) {
            throw new NoSuchElementException("Post is not found");
        }
        return optPost.get();
    }

    public List<Post> findPostsForCurrentDate() {
        return postRepository.findPostsForCurrentDate();
    }

    public List<Post> findPostsWithPhoto() {
        return postRepository.findPostsWithPhoto();
    }

    public List<Post> findPostsWithDefiniteMakeOfCar(String name) {
        return postRepository.findPostsWithDefiniteMakeOfCar(name);
    }

    public List<Post> findPostsForDefiniteUser(int userId) {
        return postRepository.findPostsForDefiniteUser(userId);
    }
}
