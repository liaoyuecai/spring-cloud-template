package org.template.cloud.service.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.template.cloud.service.service.mysql.UserService;

@RestController
public class MainController {


    @Autowired
    UserService userService;

    @RequestMapping("/hello")
    public String index(@RequestParam String name) {
        return "hello " + name;
    }
}
