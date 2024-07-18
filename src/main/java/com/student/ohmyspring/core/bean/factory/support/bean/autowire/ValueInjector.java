package com.student.ohmyspring.core.bean.factory.support.bean.autowire;

import com.student.ohmyspring.core.bean.application.ApplicationContext;
import com.student.ohmyspring.core.bean.factory.support.aware.ApplicationContextAware;
import org.jetbrains.annotations.Nullable;

import java.lang.reflect.Field;

/**
 * @author Student
 */
public interface ValueInjector extends ApplicationContextAware {

    /**
     * @param beanBeingInjected  as it says
     * @param fieldBeingInjected as it says
     * @param placeholder        this for decoupling. ${} can be retrieved from xml or annotation;
     */
    void injectValueIntoBean(Object beanBeingInjected, Field fieldBeingInjected, String placeholder);


    ApplicationContext getApplicationContext();

    default String handlePlaceholder(String placeholder) {
        if (!placeholder.startsWith("$"))
            throw new IllegalArgumentException("placeholder must start with '$' : " + placeholder);
        int start = placeholder.indexOf("{") + 1;
        int end = placeholder.indexOf("}");
        return placeholder.substring(start, end);
    }


    /**
     * FIXME: this method should be written in interface as more candidates can be added via yml or other methods. But anyway, that's a default method, implementation can override it;
     *
     *
     * @param valueStr
     * @return
     */
    @Nullable
    default String findCandidate(String valueStr) {
        ApplicationContext applicationContext = getApplicationContext();
        return applicationContext.searchSysEnv(valueStr);
    }


}
