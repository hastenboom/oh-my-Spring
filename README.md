# Main

This is the Java version of [my-Spring](https://github.com/hastenboom/my-spring) written in kotlin. The kotlin version Spring is not robust as I'm not familiar with the syntax of kotlin and it's not tested.

This project focus on how Spring utilizes the `STUDPID 3 level cache` to handle the cyclic reference problem, and also on how to use the dynamic proxy to implement the AOP.

In this version, the major parts of oh-my-Spring have been fully refactored; That brings few benefits:
  - Easy to understand and maintain.
  - Easy to test and debug.
  - Though, this project implements the Annotation only, but most of the AbstractXXXX classes have been constructed in template method pattern. Thus, it is easy to extend them to implement the xml one(😅;
 
 In terms of the design pattern:
  - single responsibility principle is emphasized.
  - open-closed principle is followed.
  - dependency injection is used to decouple the modules.
  - `factory pattern` has been applied all around.
  - `template method pattern` is applied in most Abstractxxxx
  - `proxy pattern` is implemented by the cglib and jdk dynamic proxy.
  - `chain of responsibility pattern` can be found in MethodInterceptor and MethodInvocation.

# TODO
Again, this is for educational purpose only. More important concepts like :
  - Better pointcut expression parser.
  - Lazy init.
  - Setter autowired.
  - Aware interface and callback,
  - The beanPostProcessor, beanFactoryPostProcessor and their callback,
  - The ApplicationContext is not the genuine "high-level or advanced level beanFactory" here. It focuses on the "Process", i.e., how to run the beanFactory, I didn't give the Event Listener and other features.
  - The order the AOP is not configurable.
  - Alias.
are not implemented yet. Though, I did implement most of them in the kotlin version.

# Misc
- `JDK >= 21`
- if the cglib doesn't work, try this in VM option `--add-opens java.base/java.lang=ALL-UNNAMED --add-opens java.base/sun.net.util=ALL-UNNAMED`
