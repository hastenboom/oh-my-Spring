package com.student.ohmyspring.core.bean.factory.support.bean.autowire;

import com.student.ohmyspring.core.bean.application.ApplicationContext;
import lombok.extern.slf4j.Slf4j;

import java.lang.reflect.Field;

/**
 * <p>A fairly simple implementation of the ValueInjector interface that handles only the ${} with system
 * properties.</p>
 *
 * <p>doesn't support the yml placeholder</p>
 * @author Student
 */
@Slf4j
public class SimpleValueInjector implements ValueInjector {

    ApplicationContext applicationContext;

    /**
     *
     * @param applicationContext
     * @see ApplicationContext#prepareRefresh()
     */
    @Override
    public void setApplicationContext(ApplicationContext applicationContext) {
        this.applicationContext = applicationContext;
    }


    @Override
    public void injectValueIntoBean(Object beanBeingInjected, Field fieldBeingInjected, String placeholder) {
        // ${java.version} -> java.version
        String valueStr = handlePlaceholder(placeholder);

        String candidate = findCandidate(valueStr);
        if (candidate == null) {
            log.warn("No candidate found for placeholder: {} ", placeholder);
            throw new IllegalArgumentException("No candidate found for placeholder: " + placeholder);
        }
        fieldBeingInjected.setAccessible(true);
        try {
            fieldBeingInjected.set(beanBeingInjected, candidate);
        }
        catch (IllegalAccessException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public ApplicationContext getApplicationContext() {
        if (applicationContext == null) {
            throw new IllegalStateException("ApplicationContext is not set");
        }
        return applicationContext;
    }


}
