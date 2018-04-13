package org.template.cloud.service.controller;

import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.template.cloud.service.deploy.CloudTestConfig;

@RestController
@RefreshScope
public class ConfigController {

    CloudTestConfig Config;

    @RequestMapping("/getTest")
    @ResponseBody
    public String getTest() {
        return Config.name();
    }
}
