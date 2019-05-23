package org.template.cloud.application.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ApplicationController {

    @RequestMapping("hello")
    public String checkBypass() {
        return "Hello World";
    }
}
