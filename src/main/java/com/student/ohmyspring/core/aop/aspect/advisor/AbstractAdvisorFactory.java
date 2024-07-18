package com.student.ohmyspring.core.aop.aspect.advisor;


import com.student.ohmyspring.core.bean.factory.support.bean.definition.BeanDefinition;
import org.jetbrains.annotations.NotNull;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @author Student
 */
public abstract class AbstractAdvisorFactory implements AdvisorFactory {

    List<Advisor> allAdvisors;


    public AbstractAdvisorFactory(
            @NotNull final Map<String, BeanDefinition> beanDefinitionMap)
    {
        this.allAdvisors = generateAllAdvisors(beanDefinitionMap);
    }

    @Override
    public List<Advisor> getEligibleAdvisors(Object beanBeingProxied) {

        if (allAdvisors == null) {
            throw new IllegalStateException("AdvisorFactory not initialized properly, maybe you should manually call " +
                    "the generateAllAdvisors() method?");
        }

        List<Advisor> eligibleAdvisorList = new ArrayList<>();
        Method[] methods = beanBeingProxied.getClass().getDeclaredMethods();

        for (var possibleMethod : methods) {
            for (var possibleAdvisor : allAdvisors) {
                if (possibleAdvisor.getPointCut().matches(possibleMethod)) {
                    eligibleAdvisorList.add(possibleAdvisor);
                }
            }
        }

        return eligibleAdvisorList;
    }

}
