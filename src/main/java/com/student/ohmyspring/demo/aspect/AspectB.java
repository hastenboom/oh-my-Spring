package com.student.ohmyspring.demo.aspect;

import com.student.ohmyspring.core.aop.annotation.After;
import com.student.ohmyspring.core.aop.annotation.Aspect;
import com.student.ohmyspring.core.aop.annotation.Before;
import com.student.ohmyspring.core.bean.annotation.Component;

import java.lang.reflect.Method;

/**
 * @author Student
 */
@Component
@Aspect
public class AspectB {

    @Before("execution(* com.student.ohmyspring.demo.entity.CycRefAndProxyA.*(..))")
    public void beforeA() {
        System.out.println("cycRefAndProxyA beforeA");
    }

    @After("execution(* com.student.ohmyspring.demo.entity.CycRefAndProxyA.*(..))")
    public void afterA(Method method) {
        System.out.println("cycRefAndProxyA afterA");
    }

    @After("execution(* com.student.ohmyspring.demo.entity.CycRefAndProxyB.*(..))")
    public void afterB1(Method method) {
        System.out.println("cycRefAndProxyB afterB1");
    }

    @After("execution(* com.student.ohmyspring.demo.entity.CycRefAndProxyB.*(..))")
    public void afterB2(Method method) {
        System.out.println("cycRefAndProxyB afterB2");
    }

    @Before("execution(* com.student.ohmyspring.demo.entity.CycRefAndProxyB.*(..))")
    public void beforeB1() {
        System.out.println("cycRefAndProxyB beforeB1");
    }

    @Before("execution(* com.student.ohmyspring.demo.entity.CycRefAndProxyB.*(..))")
    public void beforeB2() {
        System.out.println("cycRefAndProxyB beforeB2");
    }




/*
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
    }
*/

}
