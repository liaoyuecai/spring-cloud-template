package org.template.cloud.service.transaction.tcc;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.util.StopWatch;

/**
 * @author Unmi
 */

@Aspect
public class MethodExecutionTime {

    @Around("execution(* *.*(..)) && @annotation(org.template.cloud.service.transaction.tcc.MonitorMethod)")
    public Object profile(ProceedingJoinPoint  pjp) throws Throwable {
        StopWatch sw = new StopWatch(getClass().getSimpleName());
        try {
            sw.start(pjp.getSignature().toShortString());
            return pjp.proceed();
        } finally {
            sw.stop();
            System.out.println(sw.prettyPrint());
        }
    }
}