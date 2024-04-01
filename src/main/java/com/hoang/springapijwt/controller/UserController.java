package com.hoang.springapijwt.controller;

import com.hoang.springapijwt.models.User;
import com.hoang.springapijwt.service.user.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


import java.util.List;


@RestController
@RequestMapping("/api/user")
public class UserController {
    @Autowired
    private UserService userService;


    @GetMapping("/users")
    public ResponseEntity<List<User>> showUser(Model model) {
        return ResponseEntity.ok(userService.getAll());
    }

    @GetMapping("/users1")
    public List<User> showUser1(Model model) {
        return userService.getAll();
    }
}
