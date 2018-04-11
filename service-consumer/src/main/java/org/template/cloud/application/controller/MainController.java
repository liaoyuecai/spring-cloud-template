package org.template.cloud.application.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.template.cloud.application.remote.ServiceRemote;

@Controller
@RequestMapping("/")
public class MainController {
    @Autowired
    ServiceRemote remote;

    @RequestMapping("/hello/{name}")
    @ResponseBody
    public String index(@PathVariable("name") String name) {
        return remote.hello(name);
    }
}
