package org.template.cloud.service.transaction;

@MQTransaction
public class TransactionTest implements  TransactionTestIn{

    public void test(String a){
        System.out.println("1111111");
    }
}
