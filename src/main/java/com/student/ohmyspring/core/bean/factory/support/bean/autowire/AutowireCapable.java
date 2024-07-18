package com.student.ohmyspring.core.bean.factory.support.bean.autowire;

import com.student.ohmyspring.core.bean.factory.support.bean.definition.BeanDefinition;

/**
 * @author Student
 */
public interface AutowireCapable {
    void inject(Object beanBeingInjected, BeanDefinition bdBeingInjected);
}
