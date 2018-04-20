package org.template.cloud.service.transaction;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@Retention(RetentionPolicy.RUNTIME)
public @interface MQTransaction {
    String paramValue() default "hello world";

    Class clazz() default Object.class;
}
