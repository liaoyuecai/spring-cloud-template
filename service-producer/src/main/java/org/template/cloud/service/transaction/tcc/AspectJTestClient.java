package org.template.cloud.service.transaction.tcc;

/**
 * Created by Administrator on 2018/4/23 0023.
 */
public class AspectJTestClient {
    public static void main(String[] args) {

        new StockService().getBaseInfo("MSFT");

        //new FundService().getBaseInfo("BBBIX");
    }
}
