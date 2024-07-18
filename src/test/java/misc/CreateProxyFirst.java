package misc;

import lombok.extern.slf4j.Slf4j;
import net.sf.cglib.proxy.Enhancer;
import net.sf.cglib.proxy.MethodInterceptor;
import org.junit.jupiter.api.Test;

/**
 * @author Student
 */
@Slf4j
public class CreateProxyFirst {


    @Test
    void testCreateProxy() {
        ClassB classB = new ClassB();

        //should inject it
//        classB.setClassA(new ClassA("124213"));

        //假设这是A触发的，并且提前创建了ProxyB
        Enhancer enhancer = new Enhancer();
        enhancer.setSuperclass(ClassB.class);
        enhancer.setCallback(
                (MethodInterceptor) (proxy, method, args, methodProxy) -> {
                    log.info("before invoke method");
                    return methodProxy.invoke(classB, args);
                });

        ClassB proxyB = (ClassB) enhancer.create();

        classB.setClassA(new ClassA("124213"));
        log.info("classB.getClassA() = {}", classB.getClassA());

    }
}
