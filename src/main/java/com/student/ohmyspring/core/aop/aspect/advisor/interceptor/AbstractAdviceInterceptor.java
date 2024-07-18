package com.student.ohmyspring.core.aop.aspect.advisor.interceptor;

import com.student.ohmyspring.core.aop.aspect.PrototypeAspectBeanFactory;
import com.student.ohmyspring.core.aop.aspect.advisor.PointCut;

import java.lang.reflect.Method;

/**
 * @author Student
 */
public abstract class AbstractAdviceInterceptor implements MethodInterceptor {

    protected Method aspectMethod;
    protected PointCut pointCut;
    protected PrototypeAspectBeanFactory prototypeAspectBeanFactory;

    public AbstractAdviceInterceptor(Method aspectMethod, PointCut pointCut,
                                     PrototypeAspectBeanFactory prototypeAspectBeanFactory) {
        this.aspectMethod = aspectMethod;
        this.pointCut = pointCut;
        this.prototypeAspectBeanFactory = prototypeAspectBeanFactory;
    }

}
