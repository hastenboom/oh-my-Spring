package com.student.ohmyspring.core.aop;

import com.student.ohmyspring.core.aop.aspect.advisor.Advisor;
import com.student.ohmyspring.core.aop.aspect.interceptor.MethodInvocation;
import lombok.extern.slf4j.Slf4j;
import net.sf.cglib.proxy.Enhancer;
import net.sf.cglib.proxy.MethodInterceptor;

import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Student
 */
@Slf4j
public class ProxyFactory {

    final private Object beanBeingProxied;
    final private String beanName;
    final private List<Advisor> advisorList;

    public ProxyFactory(Object beanBeingProxied, String beanName, List<Advisor> advisorList) {
        this.beanBeingProxied = beanBeingProxied;
        this.beanName = beanName;
        this.advisorList = advisorList;
    }

    public Object getProxy() {
        Class<?>[] interfaceList = beanBeingProxied.getClass().getInterfaces();
        if (interfaceList.length == 0 || interfaceList == null) {
            return createCglibProxy();
        }
        else {
            return createJdkProxy();
        }
    }


    private Object createJdkProxy() {
        return Proxy.newProxyInstance(
                beanBeingProxied.getClass().getClassLoader(),
                beanBeingProxied.getClass().getInterfaces(),
                (proxy, methodBeingProxied, args) -> {

                    var interceptorListForThisMethod = findInterceptorListForMethod(methodBeingProxied);
                    if (!interceptorListForThisMethod.isEmpty()) {
                        MethodInvocation methodInvocation = new MethodInvocation(beanBeingProxied, args,
                                methodBeingProxied, interceptorListForThisMethod);
                        return methodInvocation.proceed();
                    }
                    else {
                        return methodBeingProxied.invoke(beanBeingProxied, args);
                    }
                }
        );
    }


    private Object createCglibProxy() {

        Enhancer enhancer = new Enhancer();
        enhancer.setSuperclass(beanBeingProxied.getClass());
        enhancer.setCallback(
                (MethodInterceptor) (proxy, methodBeingProxied, args, methodProxyBeingProxied) -> {

                    var interceptorListForThisMethod = findInterceptorListForMethod(methodBeingProxied);

                    if (!interceptorListForThisMethod.isEmpty()) {
                        MethodInvocation methodInvocation = new MethodInvocation(beanBeingProxied, args,
                                methodBeingProxied, interceptorListForThisMethod);
                        return methodInvocation.proceed();
                    }
                    else {
                        return methodBeingProxied.invoke(beanBeingProxied, args);
                    }
                });

        return enhancer.create();
    }

    private List<com.student.ohmyspring.core.aop.aspect.interceptor.MethodInterceptor> findInterceptorListForMethod(Method method) {

        var interceptorListForThisMethod =
                new ArrayList<com.student.ohmyspring.core.aop.aspect.interceptor.MethodInterceptor>();

        for (var advisor : advisorList) {
            if (advisor.getPointCut().matches(method)) {
                interceptorListForThisMethod.add(advisor.getMethodInterceptor());
            }
        }
        return interceptorListForThisMethod;
    }


}
