package com.student.ohmyspring.demo.entity;

import com.student.ohmyspring.core.bean.annotation.Autowired;
import com.student.ohmyspring.core.bean.annotation.Component;

/**
 * @author Student
 */
@Component
public class CycRefAndProxyA {

    @Autowired
    private CycRefAndProxyB cycRefAndProxyB;

    public void foo() {
        System.out.println("foo in CycRefAndProxyA");
    }

}
