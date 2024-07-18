package com.student.ohmyspring.core.bean.factory.support.bean.definition;

import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author Student
 */
@Data
@NoArgsConstructor
public class BeanDefinition {

    private Class<?> beanClass;

    private String beanName;

    private String scope;

    public boolean isSingleton() {
        return scope.equals("singleton");
    }

    public boolean isPrototype() {
        return scope.equals("prototype");
    }

}
