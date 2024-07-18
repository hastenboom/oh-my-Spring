package com.student.ohmyspring.core.bean.factory;

import com.student.ohmyspring.core.bean.application.AnnotationApplicationContext;
import com.student.ohmyspring.core.bean.application.ApplicationContext;
import com.student.ohmyspring.demo.entity.ClassA;
import com.student.ohmyspring.demo.entity.ClassB;
import org.junit.jupiter.api.Test;


import static constant.TestConstant.FILE_PATH;

class AnnotationApplicationContextTest {

    static public ApplicationContext factory = new AnnotationApplicationContext(FILE_PATH);

/*    @BeforeAll
    static void setUp() {
        factory.registerBeanDefinitionMap();
    }*/

/*    @Test
    void testRegisterBeanDefinitionMap() {
        Map<String, BeanDefinition> beanDefinitionMap = factory.getBeanDefinitionMap();
        for (var entry : beanDefinitionMap.entrySet()) {
            System.out.println(entry.getKey() + "\t :\t " + entry.getValue());
        }
    }*/

    @Test
    void test3LevelCacheInRefresh() {
        factory.refresh();
        ClassA classA = (ClassA) factory.getBean("classA");
        ClassB classB = (ClassB) factory.getBean("classB");

        System.out.println(classA.getClassB().hashCode());
        System.out.println(classB.hashCode());
    }


    @Test
    void ACircleRefTest() {
        ClassA classA = new ClassA();
        ClassB classB = new ClassB();
        classA.setClassB(classB);
        classB.setClassA(classA);
        System.out.println(classA.getClassB().hashCode());
        System.out.println(classB.hashCode());
    }

}