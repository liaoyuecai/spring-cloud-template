package org.template.cloud.application.controller;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
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
    @HystrixCommand(fallbackMethod = "fallback_test")
    public String index(@PathVariable("name") String name) {
        return remote.hello(name);
    }

    /**
     * 熔断器失败返回
     * 这里的参数必须与熔断方法参数一致
     *
     * @return
     */
    public String fallback_test(String name) {
        return "资源损坏或资源紧张";
    }
}
