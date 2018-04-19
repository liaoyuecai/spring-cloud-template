package org.template.cloud.service.remote;

import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.template.cloud.service.transaction.bean.TransactionFail;
import org.template.cloud.transaction.Transaction;
import org.template.cloud.transaction.bean.TransactionOperation;

@FeignClient(name = "transaction")
public interface TransactionRemote {

    @RequestMapping(value = "/executeOperation", method = RequestMethod.POST)
    public Integer executeOperation(@RequestParam(value = "operationId") String operationId);

    @RequestMapping(value = "/executeTransaction", method = RequestMethod.POST)
    public Integer executeTransaction(@RequestParam(value = "transactionId") String transactionId);

    @RequestMapping(value = "/getOperationStatus", method = RequestMethod.POST)
    public Integer getOperationStatus(@RequestParam(value = "operationId") String operationId);

    @RequestMapping(value = "/updateTransaction", method = RequestMethod.POST)
    public String updateTransaction(@RequestParam(value = "transaction") String transaction);

    @RequestMapping(value = "/updateOperation", method = RequestMethod.POST)
    public String updateOperation(@RequestParam(value = "operation") String operation);

    @RequestMapping(value = "/operationFail", method = RequestMethod.POST)
    public String operationFail(@RequestParam(value = "fail") String fail);
}
