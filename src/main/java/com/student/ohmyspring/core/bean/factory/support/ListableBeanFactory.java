package com.student.ohmyspring.core.bean.factory.support;

import com.student.ohmyspring.core.bean.factory.BeanFactory;
import com.student.ohmyspring.core.bean.factory.support.bean.definition.BeanDefinition;

import java.util.List;
import java.util.Map;

/**
 * @author Student
 */
public interface ListableBeanFactory extends BeanFactory {

    List<String> getAllBeansName();

    <T> List<T> getAllBeansByInterface(Class<T> interfaceClass);

    <T> List<T> getAllBeansBySuperClass(Class<T> superClass);

    Map<String, BeanDefinition> getBeanDefinitionMap();
}
