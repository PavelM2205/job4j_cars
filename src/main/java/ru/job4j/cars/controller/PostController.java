package ru.job4j.cars.controller;

import lombok.AllArgsConstructor;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import ru.job4j.cars.model.Post;
import ru.job4j.cars.service.*;

import javax.servlet.http.HttpSession;
import java.io.IOException;

@Controller
@AllArgsConstructor
public class PostController {
    private final PostService postService;
    private final CarService carService;
    private final EngineService engineService;
    private final CarBodyService carBodyService;
    private final WheelDriveService wheelDriveService;
    private final CarColourService carColourService;
    private final YearService yearService;
    private final CityService cityService;
    private final TransmissionService transmissionService;
    private final UserService userService;

    @GetMapping("/postPage/{id}")
    public String getPostPage(@PathVariable("id") int id, Model model) {
        model.addAttribute(postService.findById(id));
        return "postPage";
    }

    @GetMapping("/addPostPage")
    public String getAddPostPage(Model model) {
        model.addAttribute("cars", carService.findAll());
        model.addAttribute("engines", engineService.findAll());
        model.addAttribute("carBodies", carBodyService.findAll());
        model.addAttribute("wheelDrives", wheelDriveService.findAll());
        model.addAttribute("carColours", carColourService.findAll());
        model.addAttribute("years", yearService.findAll());
        model.addAttribute("cities", cityService.findAll());
        model.addAttribute("transmissions", transmissionService.findAll());
        return "addPost";
    }

    @PostMapping("/addPost")
    public String addPost(@ModelAttribute Post post, @RequestParam("file") MultipartFile file,
                          HttpSession session) throws IOException {
        post.setPhoto(file.getBytes());
        postService.create(post);
        return "redirect:/index";
    }

    @GetMapping("/getPostPhoto/{id}")
    public ResponseEntity<Resource> getPhoto(@PathVariable("id") int id) {
        Post post = postService.findById(id);
        System.out.println("Метод getPostPhoto/{id}");
        return ResponseEntity.ok()
                .header(String.valueOf(new HttpHeaders()))
                .contentLength(post.getPhoto().length)
                .contentType(MediaType.parseMediaType("application/octet-stream"))
                .body(new ByteArrayResource(post.getPhoto()));
    }
}
