package com.student.ohmyspring.core.bean.factory.support;


import com.student.ohmyspring.core.aop.aspect.AbstractAspectProxyFactory;
import com.student.ohmyspring.core.bean.factory.support.bean.autowire.ValueInjector;
import com.student.ohmyspring.core.resourceloader.ClassPathFileLoader;

/**
 * @author Student
 */
public interface ConfigurableBeanFactory {


    void setClassPathFileLoader(ClassPathFileLoader classPathFileLoader);

    void setValueInjector(ValueInjector valueInjector);

    void setProxyFactory(AbstractAspectProxyFactory abstractAspectProxyFactory);

    /**
     * The implementation must provide a implementation of the aspectProxyFactory.
     */

}
