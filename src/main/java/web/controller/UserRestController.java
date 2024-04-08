package web.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import web.model.User;
import web.service.UserService;

import java.util.List;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class UserRestController {
    private final UserService userService;


    @GetMapping("/users")
    public List<User> getUser(){
        return userService.getAllUser();
    }

    @GetMapping("/{id}")
    public User getUserById(@PathVariable("id")long id){
        return userService.getUserById(id);
    }

    @PostMapping("/create")
    public User create(@RequestBody User user){
        userService.createUser(user);
        return user;
    }

    @PatchMapping("/update")
    public User updateUser(@RequestBody User user){
        userService.createUser(user);
        return user;
    }

    @DeleteMapping("/delete/{id}")
    public void deleteUser(@PathVariable("id")Long id){
        userService.deleteUser(id);
    }






}
