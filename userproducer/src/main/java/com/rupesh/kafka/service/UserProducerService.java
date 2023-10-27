package com.rupesh.kafka.service;

import com.rupesh.kafka.dto.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
public class UserProducerService {

    @Autowired
    KafkaTemplate<String, Integer> kafkaTemplate;

    @Autowired
    KafkaTemplate<String, User> kafkaTemplate1;

    public void sendUserData(String name, int age) {
        kafkaTemplate.send("user-topic", name, age);
    }

    public void sendUserData(User user) {
        kafkaTemplate1.send("user-topic", user.getName(), user);
    }
}
