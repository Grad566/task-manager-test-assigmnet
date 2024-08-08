package test.task.app.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path = "")
public class WelcomeController {
    @GetMapping()
    public String greeting() {
        return String.format("Привет, используй http://localhost:8080/api/ + сущность, чтобы использовать api,\n"
                + "но сначала залогинься :) отправив запрос на http://localhost:8080/api/login \n"
                + "username: admin@example.com password: qwerty");
    }
}
