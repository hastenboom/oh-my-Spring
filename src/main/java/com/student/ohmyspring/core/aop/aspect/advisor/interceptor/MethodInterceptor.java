package com.student.ohmyspring.core.aop.aspect.advisor.interceptor;

/**
 * @author Student
 */
public interface MethodInterceptor {

    Object invoke(MethodInvocation invocation) throws Throwable;

}
