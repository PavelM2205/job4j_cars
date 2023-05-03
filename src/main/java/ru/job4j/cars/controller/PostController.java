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
import ru.job4j.cars.model.User;
import ru.job4j.cars.service.*;

import javax.servlet.http.HttpSession;
import java.io.IOException;

@Controller
@AllArgsConstructor
public class PostController {
    private final PostService postService;
    private final CarBrandService carBrandService;
    private final EngineService engineService;
    private final CarBodyService carBodyService;
    private final WheelDriveService wheelDriveService;
    private final CarColourService carColourService;
    private final YearService yearService;
    private final CityService cityService;
    private final TransmissionService transmissionService;

    @GetMapping("/postPage/{id}")
    public String getPostPage(@PathVariable("id") int id, Model model,
                              HttpSession session) {
        Post post = postService.findById(id);
        User user = (User) session.getAttribute("user");
        model.addAttribute("user", user);
        model.addAttribute("isOwn", false);
        if (user != null && post.getUser().getId() == user.getId()) {
            model.addAttribute("isOwn", true);
        }
        model.addAttribute(postService.findById(id));
        return "postPage";
    }

    @GetMapping("/addPostPage")
    public String getAddPostPage(Model model) {
        model.addAttribute("carBrands", carBrandService.findAll());
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
        User user = (User) session.getAttribute("user");
        post.setUser(user);
        post.setPhoto(file.getBytes());
        System.out.println("Print new Post:" + post);
        postService.create(post);
        return "redirect:/index";
    }

    @GetMapping("/getPostPhoto/{id}")
    public ResponseEntity<Resource> getPhoto(@PathVariable("id") int id) {
        Post post = postService.findById(id);
        return ResponseEntity.ok()
                .header(String.valueOf(new HttpHeaders()))
                .contentLength(post.getPhoto().length)
                .contentType(MediaType.parseMediaType("application/octet-stream"))
                .body(new ByteArrayResource(post.getPhoto()));
    }

    @PostMapping("/editPost")
    public String editPost(@RequestParam("status") boolean status,
                           @RequestParam("price") int price,
                           @RequestParam("postId") int id) {
        Post post = postService.findById(id);
        post.setPrice(price);
        post.setSold(status);
        postService.update(post);
        return "redirect:/postPage/" + id;
    }
}
