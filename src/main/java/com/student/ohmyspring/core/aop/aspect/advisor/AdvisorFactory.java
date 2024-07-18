package com.student.ohmyspring.core.aop.aspect.advisor;

import com.student.ohmyspring.core.bean.factory.support.bean.definition.BeanDefinition;

import java.util.List;
import java.util.Map;

/**
 * @author Student
 */
public interface AdvisorFactory {

    List<Advisor> generateAllAdvisors(Map<String, BeanDefinition> beanDefinitionMap);

    List<Advisor> getEligibleAdvisors(Object beanBeingProxied);

}
