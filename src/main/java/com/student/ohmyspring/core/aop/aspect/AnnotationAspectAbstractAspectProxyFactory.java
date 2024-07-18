package com.student.ohmyspring.core.aop.aspect;

import com.student.ohmyspring.core.aop.aspect.advisor.AnnotationAdvisorFactory;
import lombok.extern.slf4j.Slf4j;

/**
 * @author Student
 */
@Slf4j
public class AnnotationAspectAbstractAspectProxyFactory extends AbstractAspectProxyFactory {


    public AnnotationAspectAbstractAspectProxyFactory() {
        super(new AnnotationAdvisorFactory());
    }





}
