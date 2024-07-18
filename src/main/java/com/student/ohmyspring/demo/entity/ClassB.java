package com.student.ohmyspring.demo.entity;

import com.student.ohmyspring.core.bean.annotation.Autowired;
import com.student.ohmyspring.core.bean.annotation.Component;
import com.student.ohmyspring.core.bean.annotation.Scope;
import lombok.Data;

/**
 * @author Student
 */
@Component
@Scope("singleton")
@Data
public class ClassB {
    @Autowired
    private ClassA classA;
}
