package com.student.ohmyspring.core.aop.aspect.advisor.interceptor;

import com.student.ohmyspring.core.aop.aspect.PrototypeAspectBeanFactory;
import com.student.ohmyspring.core.aop.aspect.advisor.PointCut;

import java.lang.reflect.Method;

/**
 * <pre>{@code
 * @Aspect
 * @Component
 * public class LogProxy {
 *      @After(("execution(int xxx.xxx.xxx.ArithmeticCalculator.*(int, int))"))
 *      public void afterMethod(JoinPoint point){
 *          System.out.println("After method is called");
 *       }
 * }
 *   }</pre>
 *
 * @author Student
 */
public class AfterAdviceInterceptor extends AbstractAdviceInterceptor {

    public AfterAdviceInterceptor(Method aspectMethod, PointCut pointCut,
                                  PrototypeAspectBeanFactory prototypeAspectBeanFactory)
    {
        super(aspectMethod, pointCut, prototypeAspectBeanFactory);
    }

    @Override
    public Object invoke(MethodInvocation invocation) throws Throwable {

        Object ret = invocation.proceed();

        aspectMethod.invoke(prototypeAspectBeanFactory.getAspectBean(),
                invocation.getMethodBeingProxied());
        return ret;
    }
}
