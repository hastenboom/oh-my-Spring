package com.student.ohmyspring.demo.entity;

import com.student.ohmyspring.core.bean.annotation.Autowired;
import com.student.ohmyspring.core.bean.annotation.Component;

/**
 * @author Student
 */
@Component
public class CycRefAndProxyB {
    @Autowired
    private CycRefAndProxyA cycRefAndProxyA;

    public void bar() {
        System.out.println("Bar in CycRefAndProxyB");
    }
}
