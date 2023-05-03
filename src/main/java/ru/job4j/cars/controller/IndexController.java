package ru.job4j.cars.controller;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import ru.job4j.cars.model.User;
import ru.job4j.cars.service.PostService;

import javax.servlet.http.HttpSession;

@Controller
@AllArgsConstructor
public class IndexController {
    private final PostService postService;

    @GetMapping("/index")
    public String index(Model model, HttpSession session) {
        User user = (User) session.getAttribute("user");
        model.addAttribute("user", user);
        model.addAttribute("posts", postService.findAll());
        return "index";
    }

    @GetMapping("/userPosts/{user_id}")
    public String getUserPosts(@PathVariable("user_id") int id, Model model,
                               HttpSession session) {
        model.addAttribute("posts", postService.findPostsForDefiniteUser(id));
        model.addAttribute("user", session.getAttribute("user"));
        return "index";
    }
}
