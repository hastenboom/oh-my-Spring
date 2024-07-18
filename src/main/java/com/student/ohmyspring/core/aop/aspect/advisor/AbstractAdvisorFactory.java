package com.student.ohmyspring.core.aop.aspect.advisor;


import com.student.ohmyspring.core.bean.factory.support.bean.definition.BeanDefinition;
import org.jetbrains.annotations.NotNull;

import java.util.List;
import java.util.Map;

/**
 * @author Student
 */
public abstract class AbstractAdvisorFactory implements AdvisorFactory {

    List<Advisor> allAdvisors = List.of();


    public AbstractAdvisorFactory(
            @NotNull final Map<String, BeanDefinition> beanDefinitionMap)
    {
        this.allAdvisors = generateAllAdvisors(beanDefinitionMap);
    }

    @Override
    public List<Advisor> getEligibleAdvisors(Object beanBeingProxied) {

        return List.of();
    }

}
