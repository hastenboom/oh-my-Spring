package com.student.ohmyspring.demo;

import com.student.ohmyspring.core.bean.application.AnnotationApplicationContext;
import com.student.ohmyspring.core.bean.application.ApplicationContext;
import com.student.ohmyspring.demo.entity.ClassA;
import com.student.ohmyspring.demo.entity.ClassWithAspect;
import com.student.ohmyspring.demo.entity.CycRefAndProxyB;

/**
 * @author Student
 */
public class OhMySpring {

    public static String FILE_PATH = "com/student/ohmyspring/demo";


    public static void main(String[] args) {
        ApplicationContext context = new AnnotationApplicationContext(FILE_PATH);
        context.refresh();

//        proxy obj
        ClassWithAspect bean = (ClassWithAspect) context.getBean("classWithAspect");
        bean.doSomething();

//        cycle reference
        ClassA classA = (ClassA) context.getBean("classA");
        System.out.println(classA.getVendor());

        CycRefAndProxyB cycRefAndProxyB = (CycRefAndProxyB) context.getBean("cycRefAndProxyB");
        cycRefAndProxyB.bar();
    }
}
