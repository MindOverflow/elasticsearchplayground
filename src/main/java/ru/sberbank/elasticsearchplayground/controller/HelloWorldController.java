package ru.sberbank.elasticsearchplayground.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
public class HelloWorldController {
    @GetMapping("/hello")
    public ResponseEntity<List<String>> sayHello() {
        List<String> list = new ArrayList<>();
        list.add("Hello, Alexey and Anton. Welcome to the great world of Elastic Search");
        return ResponseEntity.ok(list);
    }
}
