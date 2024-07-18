package com.student.ohmyspring.core.aop.aspect;

/**
 * @author Student
 */
public class PrototypeAspectBeanFactory {

    private Class<?> aspectClazz;

    public PrototypeAspectBeanFactory(Class<?> aspectClazz) {
        this.aspectClazz = aspectClazz;
    }

    public Object getAspectBean() {
        try {
            return aspectClazz.newInstance();
        }
        catch (InstantiationException | IllegalAccessException e) {
            throw new RuntimeException(e);
        }

    }

}
