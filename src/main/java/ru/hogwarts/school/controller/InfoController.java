package ru.hogwarts.school.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Profile;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("info")
@Profile("default")
public class InfoController {

    @Value("${server.port}")
    private Integer port;

    @GetMapping()
    public String info() {
        return "Server port: " + port;
    }



}
