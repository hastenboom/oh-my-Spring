package com.student.ohmyspring.core.aop.aspect;

import com.student.ohmyspring.core.aop.aspect.advisor.AnnotationAdvisorFactory;
import com.student.ohmyspring.core.bean.application.ApplicationContext;
import lombok.extern.slf4j.Slf4j;


/**
 * @author Student
 */

@Slf4j
public class AnnotationAspectProxyFactory extends AbstractAspectProxyFactory {

    public AnnotationAspectProxyFactory(ApplicationContext applicationContext) {
        super(new AnnotationAdvisorFactory());
        super.applicationContext = applicationContext;
    }

    public AnnotationAspectProxyFactory() {
        super(new AnnotationAdvisorFactory());
    }

}
