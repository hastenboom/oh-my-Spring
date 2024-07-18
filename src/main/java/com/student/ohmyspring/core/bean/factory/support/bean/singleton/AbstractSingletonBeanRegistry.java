package com.student.ohmyspring.core.bean.factory.support.bean.singleton;

import com.student.ohmyspring.core.bean.factory.support.ObjectFactory;
import com.student.ohmyspring.core.bean.factory.support.bean.autowire.AutowireCapable;
import com.student.ohmyspring.core.bean.factory.support.bean.definition.BeanDefinition;
import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;
import org.jetbrains.annotations.Nullable;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * @author Student
 */
@Slf4j
public abstract class AbstractSingletonBeanRegistry implements SingletonBeanRegistry, AutowireCapable {

    protected Map<String, Object> singletonObjects;
    protected Map<String, Object> earlySingletonObjects;
    protected Map<String, ObjectFactory<?>> singletonFactories;

    public AbstractSingletonBeanRegistry() {
        this.singletonObjects = new HashMap<>();
        this.earlySingletonObjects = new HashMap<>();
        this.singletonFactories = new HashMap<>();
    }

    @Override
    public void registerSingleton(String beanName, @NonNull Object singletonObject)
    {
        if (containsSingleton(beanName)) {
            log.info("beanName:{} already exists, new bean will replace it", beanName);
        }

        singletonObjects.put(beanName, singletonObject);
        earlySingletonObjects.remove(beanName);
        singletonFactories.remove(beanName);
    }

    @Override
    public Object getSingleton(String beanName) {
        // TODO: implement singletonObjects
        throw new UnsupportedOperationException();
    }

    /**
     * FIXME: the real spring requires the DCL, double-checked locking
     *
     * @param beanName
     * @param allowEarlyReference
     * @return
     */
    @Override
    @Nullable
    public Object getSingleton(String beanName, boolean allowEarlyReference)
    {
        //1
        Object singletonBean = singletonObjects.get(beanName);
        if (singletonBean == null) {
            //2
            singletonBean = earlySingletonObjects.get(beanName);
            if (singletonBean == null && allowEarlyReference) {
                //3
                ObjectFactory<?> objectFactory = singletonFactories.get(beanName);
                if (objectFactory != null) {
                    singletonBean = objectFactory.getObject();
                    earlySingletonObjects.put(beanName, singletonBean);
                    singletonFactories.remove(beanName);
                }
            }
        }
        return singletonBean;
    }


    /**
     * only the completed bean cached in singletonObjects will be returned
     *
     * @param beanName
     * @return
     */
    @Override
    public boolean containsSingleton(String beanName) {
        return singletonObjects.containsKey(beanName);
    }

    @Override
    public Set<String> getSingletonNames() {
        return singletonObjects.keySet();
    }

    @Override
    public int getSingletonCount() {
        return singletonObjects.size();
    }

    @Override
    public void clearSingletons() {
        singletonObjects.clear();
        earlySingletonObjects.clear();
        singletonFactories.clear();
    }

/*    //TODO: remove me
    @Override
    public void createSingletonBean(String beanName, BeanDefinition beanDefinition) {

        Object emptyBean = createEmptyBean(beanDefinition);

        singletonFactories.put(beanName, () -> getEarlyBeanRef(beanName, emptyBean));
        earlySingletonObjects.remove(beanName);

        inject(emptyBean, beanDefinition);

    }*/


}
