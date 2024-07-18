package com.student.ohmyspring.core.bean.factory.support.bean.singleton;


import java.util.Set;

/**
 * @author Student
 */
public interface SingletonBeanRegistry {

    void registerSingleton(String beanName, Object singletonObject);

    Object getSingleton(String beanName);

    Object getSingleton(String beanName, boolean allowEarlyReference);

    boolean containsSingleton(String beanName);

    Set<String> getSingletonNames();

    int getSingletonCount();

    void clearSingletons();


}
