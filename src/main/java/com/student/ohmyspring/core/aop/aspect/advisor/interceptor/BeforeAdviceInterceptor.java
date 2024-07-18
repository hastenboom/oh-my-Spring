package com.student.ohmyspring.core.aop.aspect.advisor.interceptor;

import com.student.ohmyspring.core.aop.aspect.PrototypeAspectBeanFactory;
import com.student.ohmyspring.core.aop.aspect.advisor.PointCut;

import java.lang.reflect.Method;

/**

 * @author Student
 */
public class BeforeAdviceInterceptor extends AbstractAdviceInterceptor {

    public BeforeAdviceInterceptor(Method aspectMethod, PointCut pointCut,
                                   PrototypeAspectBeanFactory prototypeAspectBeanFactory)
    {
        super(
                aspectMethod,
                pointCut,
                prototypeAspectBeanFactory);
    }

    @Override
    public Object invoke(MethodInvocation invocation) throws Throwable {

        aspectMethod.invoke(
                prototypeAspectBeanFactory.getAspectBean(),
               null
        );
        return invocation.proceed();
    }
}
