package com.student.ohmyspring.core.aop.aspect.advisor.interceptor;

import lombok.Data;

import java.lang.reflect.Method;
import java.util.List;

/**
 * @author Student
 */
@Data
public class MethodInvocation {
    private Object beanBeingProxied;
    private Object[] args;
    private Method methodBeingProxied;
    private List<MethodInterceptor> methodInterceptorList;

    public MethodInvocation(Object beanBeingProxied, Object[] args, Method methodBeingProxied,
                            List<MethodInterceptor> methodInterceptorList)
    {
        this.beanBeingProxied = beanBeingProxied;
        this.args = args;
        this.methodBeingProxied = methodBeingProxied;
        this.methodInterceptorList = methodInterceptorList;
    }

    private int count = 1;


    //TODO
public    Object proceed() throws Throwable {
        if(count>methodInterceptorList.size()){
            return methodBeingProxied.invoke(beanBeingProxied, args);
        }
        MethodInterceptor aspectMethod = methodInterceptorList.get(count - 1);
        count+=1;
        return aspectMethod.invoke(this);
    }
}
