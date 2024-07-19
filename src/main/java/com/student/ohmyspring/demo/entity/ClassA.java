package com.student.ohmyspring.demo.entity;

import com.student.ohmyspring.core.bean.annotation.Autowired;
import com.student.ohmyspring.core.bean.annotation.Component;
import com.student.ohmyspring.core.bean.annotation.Scope;
import com.student.ohmyspring.core.bean.annotation.Value;
import lombok.Data;

/**
 * @author Student
 */
@Component
@Scope("singleton")
@Data
public class ClassA {

    @Value("${java.vm.vendor}")
    private String vendor;

    @Autowired
    private ClassB classB;


}
