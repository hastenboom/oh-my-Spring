package com.student.ohmyspring.core.bean.factory;

import cn.hutool.core.lang.Assert;
import com.student.ohmyspring.core.aop.aspect.AbstractAspectProxyFactory;
import com.student.ohmyspring.core.aop.aspect.AspectProxyFactory;
import com.student.ohmyspring.core.bean.factory.support.ConfigurableBeanFactory;
import com.student.ohmyspring.core.bean.factory.support.ListableBeanFactory;
import com.student.ohmyspring.core.bean.factory.support.bean.autowire.SimpleValueInjector;
import com.student.ohmyspring.core.bean.factory.support.bean.autowire.ValueInjector;
import com.student.ohmyspring.core.bean.factory.support.bean.definition.BeanDefinition;
import com.student.ohmyspring.core.bean.factory.support.bean.definition.BeanDefinitionRegistry;
import com.student.ohmyspring.core.bean.factory.support.bean.singleton.AbstractSingletonBeanRegistry;
import com.student.ohmyspring.core.resourceloader.ClassPathFileLoader;
import lombok.extern.slf4j.Slf4j;
import org.jetbrains.annotations.Nullable;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>The main class focus on:</p>
 * <ul>
 *     <li>provide the basic data structure for managing the bean lifecycles</li>
 *     <li>manage the bean lifecycle</li>
 * </ul>
 *
 * @author Student
 */
@Slf4j
public abstract class AbstractBeanFactory extends AbstractSingletonBeanRegistry implements ListableBeanFactory,
        BeanDefinitionRegistry, ConfigurableBeanFactory
{
    protected ClassPathFileLoader fileLoader;
    protected Map<String, BeanDefinition> beanDefinitionMap;
    protected ValueInjector valueInjector;
    protected AspectProxyFactory aspectProxyFactory;

    /**
     * TODO: should consider the default Constructor which doesn't need the packagePath but scans the current package
     * by default;
     *
     * @see com.student.ohmyspring.core.bean.factory.support.ConfigurableBeanFactory
     */
    public AbstractBeanFactory(String packagePath, AspectProxyFactory aspectProxyFactory) {
        this.fileLoader = new ClassPathFileLoader(packagePath);
        this.beanDefinitionMap = new HashMap<>();
        this.valueInjector = new SimpleValueInjector();
        this.aspectProxyFactory = aspectProxyFactory;
    }

    @Override
    public void setValueInjector(ValueInjector valueInjector) {
        this.valueInjector = valueInjector;
    }

    @Override
    public void setProxyFactory(AspectProxyFactory aspectProxyFactory) {
        this.aspectProxyFactory = aspectProxyFactory;
    }

    @Override
    public void setClassPathFileLoader(ClassPathFileLoader classPathFileLoader) {
        this.fileLoader = classPathFileLoader;
    }

    //TODO
    @Override
    @Nullable
    public Object getBean(String beanName) {

        BeanDefinition beanDefinition = beanDefinitionMap.get(beanName);
        Assert.notNull(beanDefinition, "No bean named '" + beanName + "' found");

        //singleton or not
        if (beanDefinition.isSingleton()) {
//            T bean = singletonBeanRegistry.getSingleton(beanName, requiredType, true);
            //3-level caches fail to get bean, consider creating it
            Object singleton = super.getSingleton(beanName, true);
            if (singleton == null) {
                singleton = createBean(beanName, beanDefinition);
                super.registerSingleton(beanName, singleton);
            }
            return singleton;

        }
        else if (beanDefinition.isPrototype()) {
            return createBean(beanName, beanDefinition);
        }

        throw new UnsupportedOperationException("Unsupported bean scope");
    }

    private Object createBean(String beanName, BeanDefinition beanDefinition) {

        Object emptyBean = createEmptyBean(beanDefinition);

        //singleton specific
        if (beanDefinition.isSingleton()) {
            //put the ObjectFactory into the third level cache
            super.singletonFactories.put(beanName, () -> getEarlyBeanRef(beanName, emptyBean));
//            super.singletonFactories.put(beanName, () -> aspectProxyFactory.createAspectProxy(beanName, emptyBean));
            super.earlySingletonObjects.remove(beanName);
        }

        Object exposedBean = emptyBean;

        //son implements inject()
        inject(emptyBean, beanDefinition);

        //TODO : Not finished yet
        //initializeBean

        if (beanDefinition.isSingleton()) {
            Object earlySingletonRef = super.getSingleton(beanName, false);
            if (earlySingletonRef != null) {
                exposedBean = earlySingletonRef;
            }
        }

        return exposedBean;
    }


    private Object createEmptyBean(BeanDefinition beanDefinition) {
        Class<?> beanClass = beanDefinition.getBeanClass();
        try {
            Constructor<?> defaultConstructor = beanClass.getDeclaredConstructor();
            defaultConstructor.setAccessible(true);
            return defaultConstructor.newInstance();
        }
        catch (NoSuchMethodException e) {
            log.error("No default constructor found for class: {}", beanClass.getName());
            throw new RuntimeException(e);
        }
        catch (InvocationTargetException | InstantiationException | IllegalAccessException e) {
            log.error("Failed to create empty bean for class by no-arg constructor: {}", beanClass.getName());
            throw new RuntimeException(e);
        }
    }


    //The entry for AOP proxy
    public Object getEarlyBeanRef(String beanName, Object beanBeingProxied) {
        Object beanProxy = abstractAspectProxyFactory.createAspectProxy(beanName, beanBeingProxied);

        if (beanProxy == null) {
            log.error("Failed to create AOP proxy for bean: {}", beanName);
        }

        return beanProxy;
    }

    @Override
    public List<String> getAllBeansName() {
        //TODO: implement this method
        return List.of();
    }

    @Override
    public <T> List<T> getAllBeansByInterface(Class<T> interfaceClass) {
        //TODO: implement this method
        return List.of();
    }

    @Override
    public <T> List<T> getAllBeansBySuperClass(Class<T> superClass) {
        //TODO: implement this method
        return List.of();
    }

    @Override
    public Map<String, BeanDefinition> getBeanDefinitionMap() {
        //TODO: implement this method
        return beanDefinitionMap;
    }

}
