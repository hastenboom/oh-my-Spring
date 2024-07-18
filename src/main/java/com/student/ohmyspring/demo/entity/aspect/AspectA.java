package com.student.ohmyspring.demo.entity.aspect;

import com.student.ohmyspring.core.aop.annotation.Around;
import com.student.ohmyspring.core.aop.annotation.Aspect;
import com.student.ohmyspring.core.bean.annotation.Component;

/**
 * @author Student
 */
@Aspect
@Component
public class AspectA {

    @Around("execution(* com.example.service..*(String,String))")
    public void around() {
    }

}
