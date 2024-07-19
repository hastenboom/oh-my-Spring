package com.student.ohmyspring.core.aop.aspect.advisor;

import com.student.ohmyspring.core.aop.aspect.interceptor.MethodInterceptor;
import lombok.Data;


/**
 * @author Student
 */
@Data
public class Advisor {
    private PointCut pointCut;
    private MethodInterceptor methodInterceptor;

    public Advisor(PointCut pointCut, MethodInterceptor methodInterceptor) {
        this.pointCut = pointCut;
        this.methodInterceptor = methodInterceptor;
    }
}
