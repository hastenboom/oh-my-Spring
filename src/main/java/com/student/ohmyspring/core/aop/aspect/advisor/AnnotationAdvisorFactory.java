package com.student.ohmyspring.core.aop.aspect.advisor;

import com.student.ohmyspring.core.aop.annotation.*;
import com.student.ohmyspring.core.aop.aspect.PrototypeAspectBeanFactory;
import com.student.ohmyspring.core.aop.aspect.advisor.interceptor.AfterAdviceInterceptor;
import com.student.ohmyspring.core.aop.aspect.advisor.interceptor.AroundAdviceInterceptor;
import com.student.ohmyspring.core.aop.aspect.advisor.interceptor.BeforeAdviceInterceptor;
import com.student.ohmyspring.core.bean.factory.support.bean.definition.BeanDefinition;
import org.jetbrains.annotations.NotNull;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * <p>Some basic concepts</p>
 * <ul>
 *     <li>Aspect, a class contains several advices</li>
 *     <li>Advice, also {@link com.student.ohmyspring.core.aop.aspect.advisor.interceptor.MethodInterceptor}, a method annotated with one of the following annotations: {@link Before},
 *     {@link After},
 *     {@link Around}, {@link AfterReturning}, {@link AfterThrowing}</li>
 *     <li>Advisor, an object contains an advice(transformed as methodInterceptor) and a {@link PointCut}</li>
 * </ul>
 *
 * @author Student
 */
public class AnnotationAdvisorFactory extends AbstractAdvisorFactory {

    public AnnotationAdvisorFactory(Map<String, BeanDefinition> beanDefinitionMap) {
        super(beanDefinitionMap);
    }

    @Override
    public List<Advisor> generateAllAdvisors(
            @NotNull final Map<String, BeanDefinition> beanDefinitionMap)
    {
        List<Advisor> allAdvisors = new ArrayList<>();

        //1. search for the @Aspect
        //get all BeanDefinitions
        beanDefinitionMap.values().stream()
                .map(BeanDefinition::getBeanClass)
                .filter(clazz -> clazz.isAnnotationPresent(Aspect.class))

                //2. transform the @Aspect into Advisor
                .forEach(clazz -> allAdvisors.addAll(transformAspect2Advisor(clazz)));

        return allAdvisors;
    }


    /**
     * <p>An aspect class contains several advices</p>
     * <pre>{@code
     *   @Aspect
     *   public class AspectA{
     *      @Around("execution(* com.example.service..login(String, String))")
     *      public Object around(ProceedingJoinPoint joinPoint) throws Throwable {
     *          // do something before
     *          Object result = joinPoint.proceed();
     *          // do something after
     *          return result;
     *          }
     *      @After
     *      public void after() {
     *          // do something after
     *      }
     *   }
     *   }</pre>
     *
     * @param aspectClass
     * @return
     */
    public List<Advisor> transformAspect2Advisor(Class<?> aspectClass) {
        Method[] methods = aspectClass.getDeclaredMethods();

        for (var adviceMethod : methods) {

            PrototypeAspectBeanFactory prototypeAspectBeanFactory = new PrototypeAspectBeanFactory(aspectClass);

            if (adviceMethod.isAnnotationPresent(Before.class)) {
                String expression = adviceMethod.getAnnotation(Before.class).value();
                PointCut pointCut = new PointCut(expression);

                BeforeAdviceInterceptor beforeAdviceInterceptor = new BeforeAdviceInterceptor(adviceMethod, pointCut,
                        prototypeAspectBeanFactory);

                Advisor advisor = new Advisor(pointCut, beforeAdviceInterceptor);
                allAdvisors.add(advisor);
            }
            else if (adviceMethod.isAnnotationPresent(After.class)) {

                String expression = adviceMethod.getAnnotation(After.class).value();
                PointCut pointCut = new PointCut(expression);
                AfterAdviceInterceptor afterAdviceInterceptor= new AfterAdviceInterceptor(adviceMethod, pointCut,
                        prototypeAspectBeanFactory);

                Advisor advisor = new Advisor(pointCut, afterAdviceInterceptor);
                allAdvisors.add(advisor);
            }
            else if (adviceMethod.isAnnotationPresent(Around.class)) {

                String expression = adviceMethod.getAnnotation(After.class).value();
                PointCut pointCut = new PointCut(expression);
                AroundAdviceInterceptor aroundAdviceInterceptor= new AroundAdviceInterceptor(adviceMethod, pointCut,
                        prototypeAspectBeanFactory);

                Advisor advisor = new Advisor(pointCut, aroundAdviceInterceptor);
                allAdvisors.add(advisor);
            }
            else if (adviceMethod.isAnnotationPresent(AfterReturning.class)) {
                //TODO
                throw new UnsupportedOperationException("AfterReturning is not supported yet");
            }
            else if (adviceMethod.isAnnotationPresent(AfterThrowing.class)) {
                //TODO
                throw new UnsupportedOperationException("AfterThrowing is not supported yet");
            }


        }
        return allAdvisors;
    }


}





