package com.student.ohmyspring.core.aop.aspect.advisor;

import com.student.ohmyspring.demo.entity.ClassWithAspect;
import org.junit.jupiter.api.Test;

import java.lang.reflect.Method;

class PointCutTest {

    @Test
    void parseExpression() {
//        new PointCut("execution(* com.example.service.*.*(..))");
//        new PointCut("execution(* com.example.service.*.method(..))");
//        new PointCut("execution(* com.example.service.HelloService.method(..))");
        PointCut pointCut = new PointCut("execution(* com.student.ohmyspring.demo.entity.ClassWithAspect.*(..))");

        System.out.println(pointCut);
        Class<ClassWithAspect> classWithAspectClass = ClassWithAspect.class;

        Method[] methods = classWithAspectClass.getMethods();
        for (Method method : methods) {
            System.out.println( method.getName() + "  :  " + pointCut.matches(method));
        }

    }

    @Test
    void StringTest() {
        String str = "*                 com.example.service.*.*(..)";
        String str2 = "String com.example.service.*.*(..)";
        String str3 = " * com.example.service.*.*(..)";

        String[] split = str.split("\\s+");
        String[] split2 = str2.split("\\s+");
        String[] split3 = str3.trim().split("\\s+");
        System.out.println();


    }
}