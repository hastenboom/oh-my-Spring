package com.student.ohmyspring.core.aop.aspect.advisor;


import lombok.Data;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Student
 */
@Data
public abstract class AbstractAdvisorFactory implements AdvisorFactory {

    List<Advisor> allAdvisors = new ArrayList<>();

    public AbstractAdvisorFactory() {
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
