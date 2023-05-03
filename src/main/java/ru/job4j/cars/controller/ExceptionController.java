package ru.job4j.cars.controller;

import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.ModelAndView;
import ru.job4j.cars.exception.UserWithSuchLoginAlreadyExists;
import ru.job4j.cars.exception.UserWithSuchLoginAndPasswordDoesNotExist;


@ControllerAdvice
public class ExceptionController {
    @ExceptionHandler(UserWithSuchLoginAlreadyExists.class)
    public ModelAndView userWithSuchLoginAlreadyExists() {
        ModelAndView mv = new ModelAndView();
        mv.addObject("userExist", true);
        mv.setViewName("registrationPage");
        return mv;
    }

    @ExceptionHandler(UserWithSuchLoginAndPasswordDoesNotExist.class)
    public ModelAndView userWithSuchLoginAndPasswordDoesNotExist() {
        ModelAndView mv = new ModelAndView();
        mv.addObject("userIsNotFound", true);
        mv.setViewName("loginPage");
        return mv;
    }
}
