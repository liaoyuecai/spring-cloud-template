package org.template.cloud.service.transaction.tcc;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;

/**
 * Created by Administrator on 2018/4/23 0023.
 */
@Component
@Aspect
public class AspectTest {
    @Around("@annotation(org.springframework.transaction.annotation.Transactional)")
    public Object profile(ProceedingJoinPoint point) throws Throwable {
        System.out.println(111111111);
        MethodSignature signature = (MethodSignature) point.getSignature();
        Method method = signature.getMethod();
        Class<?> clazz = point.getTarget().getClass();
        Object[] args = point.getArgs();
        Method thisMethod = clazz.getMethod(method.getName(), method.getParameterTypes());
        Object res = point.proceed();
//        throw new RuntimeException();
        return res;
    }
    @Around("this(org.springframework.transaction.annotation.Transactional) && execution( * *(..))")
    public Object test(ProceedingJoinPoint point) throws Throwable {
        System.out.println(111111111);
        MethodSignature signature = (MethodSignature) point.getSignature();
        Method method = signature.getMethod();
        Class<?> clazz = point.getTarget().getClass();
        Object[] args = point.getArgs();
        Method thisMethod = clazz.getMethod(method.getName(), method.getParameterTypes());
        Object res = point.proceed();
        return res;
    }
}
