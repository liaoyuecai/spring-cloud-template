package org.template.cloud.bean.tcc;

import com.alibaba.fastjson.JSON;

import java.util.*;

public class TCCTransaction {
    String id = UUID.randomUUID().toString();

    List<String> sort = new ArrayList<String>();

    Map<String, TCCOperation> operations = new HashMap<String, TCCOperation>();

    public TCCTransaction addOperation(String server, String service, String method, Object... params) {
        String operation = server + "." + service + "." + method;
        sort.add(operation);
        operations.put(operation, new TCCOperation(server, service, method, JSON.toJSONString(params)));
        return this;
    }

    public TCCTransaction addOperation(String operation, Object... params) throws IllegalAccessException {
        String[] settings = operation.split("\\.");
        if (settings.length != 3) {
            throw new IllegalAccessException("please use this format like 'server.service.method'");
        }
        sort.add(operation);
        operations.put(operation, new TCCOperation(settings[0], settings[1], settings[2], JSON.toJSONString(params)));
        return this;
    }

}
