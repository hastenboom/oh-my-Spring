package com.student.ohmyspring.core.aop.aspect;

import com.student.ohmyspring.core.aop.aspect.advisor.Advisor;
import com.student.ohmyspring.core.aop.aspect.advisor.AdvisorFactory;
import com.student.ohmyspring.core.aop.aspect.advisor.AnnotationAdvisorFactory;
import com.student.ohmyspring.core.bean.application.ApplicationContext;
import com.student.ohmyspring.core.bean.factory.support.bean.definition.BeanDefinition;
import lombok.extern.slf4j.Slf4j;
import org.jetbrains.annotations.Nullable;

import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * @author Student
 */
@Slf4j
public abstract class AbstractAspectProxyFactory implements AspectProxyFactory {

    /**
     * Not null, the creation timing of this {@code AbstractAspectProxyFactory }is after the
     * {@code ApplicationContextAware}.
     */
    protected ApplicationContext applicationContext;

    /**
     * @param applicationContext
     * @see ApplicationContext#prepareRefresh()
     */
    @Override
    public void setApplicationContext(ApplicationContext applicationContext) {
        this.applicationContext = applicationContext;
    }





    /**
     * The set of beans (records the beanName only) that have been proxied by this factory.
     */
    protected Set<String> proxiedBeans = new HashSet<>();

    protected AdvisorFactory advisorFactory;

    public AbstractAspectProxyFactory() {
        this.advisorFactory = new AnnotationAdvisorFactory();
    }


    @Override
    @Nullable
    public Object createAspectProxy(String beanName, Object beanBeingProxied) {
        if (proxiedBeans.contains(beanName)) {
            log.warn("The bean [{}] has already been proxied by this factory, skip it.", beanName);
            return null;
        }

        Map<String, BeanDefinition> beanDefinitionMap = applicationContext.getBeanDefinitionMap();
        List<Advisor> allAdvisors = advisorFactory.generateAllAdvisors(beanDefinitionMap);

        List<Advisor> eligibleAdvisors = advisorFactory.getEligibleAdvisors(allAdvisors, beanName);


        //create the aspect proxy successfully
        proxiedBeans.add(beanName);
        return null;
    }

}
