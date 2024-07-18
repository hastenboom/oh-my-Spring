package com.student.ohmyspring.core.bean.application;


import com.student.ohmyspring.core.aop.aspect.AspectProxyFactory;
import com.student.ohmyspring.core.bean.factory.AbstractBeanFactory;
import com.student.ohmyspring.core.bean.factory.support.bean.autowire.ValueInjector;
import com.student.ohmyspring.core.bean.factory.support.bean.definition.BeanDefinition;
import lombok.extern.slf4j.Slf4j;
import lombok.val;
import org.jetbrains.annotations.Nullable;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * <p>This class focus on {@code Process}</p>
 *
 * @author Student
 */
@Slf4j
public abstract class ApplicationContext extends AbstractBeanFactory {

    protected Map<String, String> sysVariablesCache;
    protected Map<String, Object> sysPropertiesCache;


    public ApplicationContext(String packagePath, AspectProxyFactory aspectProxyFactory) {
        super(packagePath, aspectProxyFactory);
        this.sysVariablesCache = new HashMap<>();
        this.sysPropertiesCache = new HashMap<>();
    }


    public void refresh() {
        prepareRefresh();

        //TODO: handle the aware?

        //TODO: fill the 3-level cache;
        initializeBean();

    }

    /**
     * <p>prepare few things</p>
     * <ol>
     *     <li>cache system variables and system properties for injection</li>
     *     <li>create the bean definition map</li>
     *     <li>handle the aware</li>
     * </ol>
     *
     * @see com.student.ohmyspring.core.bean.factory.support.aware.ApplicationContextAware
     * @see ValueInjector#setApplicationContext(ApplicationContext)
     */
    private void prepareRefresh() {
        this.sysVariablesCache = System.getenv();

        for (Map.Entry<Object, Object> entry : System.getProperties().entrySet()) {
            sysPropertiesCache.put((String) entry.getKey(), entry.getValue());
        }

        log.debug("System variables:");
        for (val sysVariable : sysVariablesCache.entrySet()) {
            log.debug("\t\t{} = {}", sysVariable.getKey(), sysVariable.getValue());
        }

        log.debug("System properties:");
        for (val sysProperty : sysPropertiesCache.entrySet()) {
            log.debug("\t\t{} = {}", sysProperty.getKey(), sysProperty.getValue());
        }

        //son.registerBeanDefinitionMap();
        registerBeanDefinitionMap();

        for (Map.Entry<String, BeanDefinition> entry : beanDefinitionMap.entrySet()) {
            log.info("Key: {}, Value: {}", entry.getKey(), entry.getValue());
        }

        super.valueInjector.setApplicationContext(this);
        super.aspectProxyFactory.setApplicationContext(this);
    }


    //TODO: not finished yet
    private void initializeBean() {
        Set<String> beanNameSet = beanDefinitionMap.keySet();
        beanNameSet.forEach(this::getBean);
    }


    @Nullable
    public String searchSysEnv(String key) {
        String value = null;
        if ((value = sysVariablesCache.get(key)) != null) {
            return value;
        }
        else if ((value = sysPropertiesCache.get(key).toString()) != null) {
            return value;
        }
        return value;
    }
}
