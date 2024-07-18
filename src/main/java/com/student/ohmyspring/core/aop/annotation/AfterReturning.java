package com.student.ohmyspring.core.aop.annotation;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@Retention(RetentionPolicy.RUNTIME)
public @interface AfterReturning
{
    String value();
}
