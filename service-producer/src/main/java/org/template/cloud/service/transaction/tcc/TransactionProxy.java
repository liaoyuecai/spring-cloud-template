package org.template.cloud.service.transaction.tcc;


import org.springframework.cglib.proxy.InvocationHandler;
import org.springframework.cglib.proxy.Proxy;
import org.template.cloud.service.transaction.MQTransaction;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;

public class TransactionProxy implements InvocationHandler {
    // 2、创建被代理对象
    private Object target;
    // 3、创建代理对象，参数是要被代理的对象，返回值是代理对象
    public static Object getInstance(Object o) {
        TransactionProxy proxy = new TransactionProxy();
        proxy.target = o;
        Object result = Proxy.newProxyInstance(o.getClass().getClassLoader(),
                o.getClass().getInterfaces(), proxy);
        return result;
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args)
            throws Throwable {
        Object o = null;
        try {
            MQTransaction a = method.getAnnotation(MQTransaction.class);
            Annotation[][] sa = method.getParameterAnnotations();
            Class[] dd = method.getParameterTypes();
            Class clazz = a.clazz();
            if (method.isAnnotationPresent(MQTransaction.class)) { // 检查该方法上是否有LogInf注解
                System.out.println("执行前");
            }
            o = method.invoke(target, args);
        }catch (Exception e){

        }
        return o;
    }
}
