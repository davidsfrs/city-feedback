package com.cityfeedback.backend;

import io.swagger.v3.oas.annotations.Hidden;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HelloController {

    @Hidden
    @GetMapping("/api/hello")
    public String hello() {
        return "Welcome to City Feedback!";
    }
}
