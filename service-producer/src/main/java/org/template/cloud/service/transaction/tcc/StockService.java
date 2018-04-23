package org.template.cloud.service.transaction.tcc;

/**
 * @author Unmi
 */
public class StockService {

    @MonitorMethod
    public String getBaseInfo(String ticker){
        System.out.println(111111111);
        try {
            Thread.sleep(500);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return "";
    }
}
