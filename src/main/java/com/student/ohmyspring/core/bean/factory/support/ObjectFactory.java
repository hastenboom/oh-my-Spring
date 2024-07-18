package com.student.ohmyspring.core.bean.factory.support;

/**
 * TODO: used for create proxy or normal bean
 *
 * @author Student
 */
@FunctionalInterface
public interface ObjectFactory<T> {
    public T getObject();
}
