package com.student.ohmyspring.core.aop.aspect;

import com.student.ohmyspring.core.bean.factory.support.aware.ApplicationContextAware;

/**
 * @author Student
 */
public interface AspectProxyFactory extends ApplicationContextAware {
    Object createAspectProxy(String beanName, Object beanBeingProxied);
}
