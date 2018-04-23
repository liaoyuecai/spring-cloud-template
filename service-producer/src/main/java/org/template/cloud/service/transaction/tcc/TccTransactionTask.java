package org.template.cloud.service.transaction.tcc;

import com.alibaba.fastjson.JSON;
import org.springframework.stereotype.Component;
import org.template.cloud.bean.tcc.TCCTransaction;

@Component
public class TccTransactionTask {
    //    @Autowired
    public void receive(String message) {
        TCCTransaction transaction = JSON.parseObject(message, TCCTransaction.class);
//        try {
//            String serviceName = "bankCardService";
//            String methodName = "update";
//            List<Object> list = new ArrayList();
//            BankCard card = new BankCard();
//            card.setId("1");
//            card.setUserId("1");
//            card.setBalance(10);
//            list.add(card);
//            Object service = TccManager.serviceMap.get(serviceName);
//            Class[] classes = TccManager.paramMap.get(serviceName + "." + methodName);
//            if (classes == null) {
//                Method execute = service.getClass().getDeclaredMethod(methodName);
//                execute.invoke(service);
//            } else {
//                String params = JSON.toJSONString(list);
//                JSONArray paramList = JSON.parseArray(params);
//                List<Object> paramList = JSON.parseObject(params, List.class);
//                Method execute = service.getClass().getDeclaredMethod(methodName, classes);
//                execute.invoke(service, paramList);
//            }
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
    }
}
