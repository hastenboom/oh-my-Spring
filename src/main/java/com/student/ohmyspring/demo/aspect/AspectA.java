package com.student.ohmyspring.demo.aspect;

import com.student.ohmyspring.core.aop.annotation.After;
import com.student.ohmyspring.core.aop.annotation.Aspect;
import com.student.ohmyspring.core.aop.annotation.Before;
import com.student.ohmyspring.core.bean.annotation.Component;

import java.lang.reflect.Method;

/**
 * @author Student
 */
@Aspect
@Component
public class AspectA {

    @Before("execution(* com.student.ohmyspring.demo.entity.ClassWithAspect.*(..))")
    public void before() {
        System.out.println("before1");
    }

    @After("execution(* com.student.ohmyspring.demo.entity.ClassWithAspect.*(..))")
    public void after(Method method) {
        System.out.println("after1");
    }
/*
    @After("execution(* com.student.ohmyspring.demo.entity.ClassWithAspect.*(..))")
    public void after2(Method method) {
        System.out.println("after2");
    }

    @Around("execution(* com.student.ohmyspring.demo.entity.ClassWithAspect.*(..))")
    public void around(MethodInvocation invocation) {
        System.out.println("around : before");

        try {
            invocation.proceed();
        }
        catch (Throwable e) {
            throw new RuntimeException(e);
        }

        System.out.println("around :after");
    }

    @Before("execution(* com.student.ohmyspring.demo.entity.ClassWithAspect.*(..))")
    public void before2() {
        System.out.println("before2");
    }*/


}
