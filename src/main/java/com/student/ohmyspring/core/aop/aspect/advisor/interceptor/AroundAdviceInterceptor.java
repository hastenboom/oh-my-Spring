package com.student.ohmyspring.core.aop.aspect.advisor.interceptor;

import com.student.ohmyspring.core.aop.aspect.PrototypeAspectBeanFactory;
import com.student.ohmyspring.core.aop.aspect.advisor.PointCut;

import javax.xml.crypto.AlgorithmMethod;
import java.lang.reflect.Method;

/**
 * <p> A typical around advice looks like</p>
 *
 *
 * <pre>{@code
 *   @Aspect
 *   public class AspectA{
 *      @Around("execution(* com.example.service..login(String, String))")
 *      public Object around(ProceedingJoinPoint joinPoint) throws Throwable {
 *          // do something before
 *          print("I'm before !!!!!!!")
 *          Object result = invocation.proceed();
 *          // do something after
 *
 *          print("I'm after!!!!!!!")
 *          return result;
 *          }
 *      @After
 *      public void after() {
 *          // do something after
 *      }
 *   }
 *   }</pre>
 * <p> The joinPoint is the Invocation with some "record"  concepts</p>
 *
 * @author Student
 */
public class AroundAdviceInterceptor extends AbstractAdviceInterceptor {

    public AroundAdviceInterceptor(Method aspectMethod, PointCut pointCut,
                                   PrototypeAspectBeanFactory prototypeAspectBeanFactory)
    {
        super(aspectMethod, pointCut, prototypeAspectBeanFactory);
    }

    @Override
    public Object invoke(MethodInvocation invocation) throws Throwable {
        aspectMethod.invoke(prototypeAspectBeanFactory.getAspectBean(), invocation);
        return invocation.proceed();
    }
}
