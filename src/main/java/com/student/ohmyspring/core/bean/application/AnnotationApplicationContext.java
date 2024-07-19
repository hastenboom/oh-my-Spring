package com.student.ohmyspring.core.bean.application;

import com.student.ohmyspring.core.aop.aspect.AnnotationAspectProxyFactory;
import com.student.ohmyspring.core.bean.annotation.Autowired;
import com.student.ohmyspring.core.bean.annotation.Component;
import com.student.ohmyspring.core.bean.annotation.Scope;
import com.student.ohmyspring.core.bean.annotation.Value;
import com.student.ohmyspring.core.bean.factory.support.bean.definition.BeanDefinition;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.lang.reflect.Field;
import java.util.List;

/**
 * <p>This class focus on Configuration</p>
 *
 * @author Student
 */
@Slf4j
public class AnnotationApplicationContext extends ApplicationContext {

    public AnnotationApplicationContext(String packagePath) {
        super(packagePath, new AnnotationAspectProxyFactory());
    }

    @Override
    public void registerBeanDefinitionMap() {
        try {
            List<String> classFileFullPathList = fileLoader.getClassFileFullPath();

            classFileFullPathList.stream()
                    .map((classFileFullPath) -> {
                        try {
                            return Class.forName(classFileFullPath);
                        }
                        catch (ClassNotFoundException e) {
                            throw new RuntimeException(e);
                        }
                    })
                    .filter((aClass) -> aClass.isAnnotationPresent(Component.class))
                    .map(this::createBeanDefinition)
                    .forEach((beanDefinition) -> beanDefinitionMap.put(beanDefinition.getBeanName(),
                            beanDefinition));

        }
        catch (IOException e) {
            throw new RuntimeException(e);
        }
    }


    private BeanDefinition createBeanDefinition(Class<?> clazz) {

        BeanDefinition beanDefinition = new BeanDefinition();
        beanDefinition.setBeanClass(clazz);

        String classNameOnly = clazz.getSimpleName();
        beanDefinition.setBeanName(classNameOnly.substring(0, 1).toLowerCase() + classNameOnly.substring(1));

        if (clazz.isAnnotationPresent(Scope.class)) {
            beanDefinition.setScope(clazz.getAnnotation(Scope.class).value());
        }
        else {
            beanDefinition.setScope("singleton");
        }
        return beanDefinition;
    }

    /**
     * <p>FIXME: Simple implementation of injection, it doesn't support the setter method injection;</p>
     * <p>FIXME: Only concern the name injection of @Autowired</p>
     * <pre>{@code
     *   public class A{
     *      @Autowired
     *      private B b;
     *   }
     *   }</pre>
     * <p>the bean always registers the name of B, if you set {@code private B b1;}, this framework fails to inject
     * it</p>
     *
     * <p>Spring在实现这部分的时候是需要构筑另一种上下文环境的，类似于我之前想在BeanDefinition中直接添加依赖信息</p>
     *
     * @param beanBeingInjected it's an empty object
     * @param bdBeingInjected
     */
    @Override
    public void inject(Object beanBeingInjected, BeanDefinition bdBeingInjected) {

        Field[] allFields = bdBeingInjected.getBeanClass().getDeclaredFields();

        for (Field fieldBeingInjected : allFields) {
            //handle with @Value annotation
            if (fieldBeingInjected.isAnnotationPresent(Value.class)) {
                //placeholder looks like ${java.home}
                String placeholder = fieldBeingInjected.getAnnotation(Value.class).value();
                valueInjector.injectValueIntoBean(beanBeingInjected, fieldBeingInjected, placeholder);
            }
            else if (fieldBeingInjected.isAnnotationPresent(Autowired.class)) {
                fieldBeingInjected.setAccessible(true);
                try {
                    fieldBeingInjected.set(beanBeingInjected, super.getBean(fieldBeingInjected.getName()));
                }
                catch (IllegalAccessException e) {
                    throw new RuntimeException(e);
                }
            }

        }
    }


}
