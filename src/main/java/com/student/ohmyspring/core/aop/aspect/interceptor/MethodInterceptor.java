package com.student.ohmyspring.core.aop.aspect.interceptor;

/**
 * @author Student
 */
public interface MethodInterceptor {

    Object invoke(MethodInvocation invocation) throws Throwable;

}
