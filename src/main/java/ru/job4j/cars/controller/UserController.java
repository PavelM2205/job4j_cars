package ru.job4j.cars.controller;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import ru.job4j.cars.model.User;
import ru.job4j.cars.service.UserService;

import javax.servlet.http.HttpSession;

@Controller
@AllArgsConstructor
public class UserController {
    private final UserService userService;

    @GetMapping("/registrationPage")
    public String getRegistrationPage() {
        return "registrationPage";
    }

    @PostMapping("/addUser")
    public String addUser(@ModelAttribute User user) {
        userService.create(user);
        return "redirect:/loginPage";
    }

    @GetMapping("/loginPage")
    public String login() {
        return "loginPage";
    }

    @PostMapping("/login")
    public String login(@RequestParam(name = "login") String login,
                        @RequestParam(name = "password") String password,
                        HttpSession session) {
        User foundUser = userService.findByLoginAndPassword(login, password);
        session.setAttribute("user", foundUser);
        return "redirect:/index";
    }

    @GetMapping("/logout")
    public String logout(HttpSession session) {
        session.invalidate();
        return "redirect:/index";
    }
}
