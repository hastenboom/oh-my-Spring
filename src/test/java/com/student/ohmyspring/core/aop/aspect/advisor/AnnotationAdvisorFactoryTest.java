package com.student.ohmyspring.core.aop.aspect.advisor;

import com.student.ohmyspring.core.bean.application.AnnotationApplicationContext;
import com.student.ohmyspring.core.bean.factory.support.bean.definition.BeanDefinition;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Map;

import static constant.TestConstant.FILE_PATH;
import static org.junit.jupiter.api.Assertions.*;

class AnnotationAdvisorFactoryTest {

    @Test
    void generateAllAdvisors() {
        AnnotationApplicationContext context = new AnnotationApplicationContext(FILE_PATH);
        context.registerBeanDefinitionMap();
        AnnotationAdvisorFactory annotationAdvisorFactory = new AnnotationAdvisorFactory();
        List<Advisor> allAdvisors = annotationAdvisorFactory.generateAllAdvisors(context.getBeanDefinitionMap());

    }
}