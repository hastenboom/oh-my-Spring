package com.student.ohmyspring.core.aop.aspect.advisor;

import com.student.ohmyspring.core.aop.annotation.Aspect;
import com.student.ohmyspring.core.bean.factory.support.bean.definition.BeanDefinition;
import lombok.val;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
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
                .forEach(clazz -> allAdvisors.addAll(transformAspectToAdvisor(clazz)));

        return allAdvisors;
    }


    /**
     *
     * <p>An aspect class contains several advices</p>
     * <pre>{@code
     *   @Aspect
     *   public class AspectA{
     *      @Around()
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
    private List<Advisor> transformAspectToAdvisor(Class<?> aspectClass) {

    }


}





