package misc;

import lombok.extern.slf4j.Slf4j;
import net.sf.cglib.proxy.Enhancer;
import net.sf.cglib.proxy.MethodInterceptor;
import org.junit.jupiter.api.Test;

import java.lang.reflect.Proxy;

/**
 * @author Student
 */
@Slf4j
public class InjectObjFirst {

    @Test
    void usingJDKProxy() {

        //emptyB
        ClassB classB = new ClassB();

        //inject emptyB
        classB.setClassA(
                //emptyA and inject emptyA
                new ClassA("fsdfdsfsdf")
        );


        // Create a proxy for ClassB
        IClassB proxyB = (IClassB) Proxy.newProxyInstance(
                classB.getClass().getClassLoader(),
                classB.getClass().getInterfaces(),
                (proxy, method, args1) -> {
                    if ("getClassA".equals(method.getName())) {
                        System.out.println("B has been proxied when getting ClassA");
                    }
                    return method.invoke(classB, args1);
                }
        );

        // Now we can use the proxyB to get the original ClassA instance without re-injecting it.
        ClassA classA1 = proxyB.getClassA();
        System.out.println(classA1.getName());
    }


    @Test
    void usingCgLibProxy() {
        //emptyB
        ClassB classB = new ClassB();

        //inject emptyB
        classB.setClassA(
                //emptyA and inject emptyA
                new ClassA("fsdfdsfsdf")
        );


        Enhancer enhancer = new Enhancer();
        enhancer.setSuperclass(ClassB.class);
        enhancer.setCallback(
                (MethodInterceptor) (proxy, method, args, methodProxy) -> {
                    log.info("method: {}", method.getName());
                    return methodProxy.invoke(classB, args);
                });
        ClassB proxyB = (ClassB) enhancer.create();

        log.info("proxyB: {}", proxyB);


    }
}
