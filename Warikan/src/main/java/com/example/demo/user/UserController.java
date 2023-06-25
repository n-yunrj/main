package com.example.demo.user;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.entity.User;
import com.example.demo.service.UserService;

@RestController
@RequestMapping("/setting")
public class UserController {
    
	@Autowired
    private UserService userService;

    @GetMapping
    public List<User> getAllPayments() {
        return userService.getAll();
    }
}
