package com.rupesh.kafka.controller;

import com.rupesh.kafka.dto.User;
import com.rupesh.kafka.service.UserProducerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/userapi")
public class UserController {

    @Autowired
    private UserProducerService userProducerService;

    @PostMapping("/publishUserData/{name}/{age}")
    public void sendUserData(@PathVariable("name") String name, @PathVariable("age") int age) {
        userProducerService.sendUserData(name, age);
    }

    @PostMapping("/publishUserData")
    public void sendUserData(@RequestBody User user) {
        userProducerService.sendUserData(user);
    }
}
