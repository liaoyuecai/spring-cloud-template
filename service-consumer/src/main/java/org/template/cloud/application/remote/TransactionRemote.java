package org.template.cloud.application.remote;

import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name = "transaction")
public interface TransactionRemote {

    @RequestMapping(value = "/transaction", method = RequestMethod.POST)
    String transaction(@RequestParam(value = "transaction") String transaction);
}
