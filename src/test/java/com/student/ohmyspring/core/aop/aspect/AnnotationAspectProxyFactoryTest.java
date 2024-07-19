package com.student.ohmyspring.core.aop.aspect;

import com.student.ohmyspring.core.bean.application.AnnotationApplicationContext;
import com.student.ohmyspring.core.bean.application.ApplicationContext;
import com.student.ohmyspring.demo.entity.ClassWithAspect;
import org.junit.jupiter.api.Test;

import static constant.TestConstant.FILE_PATH;

class AnnotationAspectProxyFactoryTest {

    ApplicationContext context = new AnnotationApplicationContext(FILE_PATH);
    AbstractAspectProxyFactory factory = new AnnotationAspectProxyFactory(context);

    @Test
    void testCreateAspectProxy() {
        context.prepareRefresh();
        ClassWithAspect classWithAspect = (ClassWithAspect)factory.createAspectProxy(
                "classWithAspect",
                new ClassWithAspect());

        classWithAspect.doSomething();
    }

}