package com.student.ohmyspring.core.bean.factory.support.aware;

import com.student.ohmyspring.core.bean.application.ApplicationContext;

/**
 * @author Student
 */
public interface ApplicationContextAware {

    void setApplicationContext(ApplicationContext applicationContext);
}
