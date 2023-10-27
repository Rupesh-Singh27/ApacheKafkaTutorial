package com.rupesh.kafka.service;

import com.rupesh.kafka.dto.User;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
public class UserConsumerService {

    @KafkaListener(topics = {"user-topic"})
    public void consumeData(User user) {
        System.out.println("User age is: " + user.getAge() + " " + user.getFavGenre());
    }

    /*@KafkaListener(topics = {"user-topic"})
    public void consumeData(int age) {
        System.out.println("User age is: " + age);
    }*/
}
