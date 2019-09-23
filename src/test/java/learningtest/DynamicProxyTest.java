package learningtest;

import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

import static org.assertj.core.api.Assertions.assertThat;

public class DynamicProxyTest {
    private static final Logger log = LoggerFactory.getLogger(DynamicProxyTest.class);

    @Test
    void adjustUpperCase() {
        Hello hello = new HelloTarget("eunsukko");

        Hello proxiedHello = (Hello) Proxy.newProxyInstance(
                getClass().getClassLoader(), // https://javacan.tistory.com/entry/1
                new Class[]{Hello.class}, // interface class for adjusting proxy
                new UppercaseHandler(hello)
        );

        assertThat(hello.hello()).isEqualTo("Hello eunsukko");
        assertThat(proxiedHello.hello()).isEqualTo("HELLO EUNSUKKO");
    }


    public interface Hello {
        String hello();
    }

    public class HelloTarget implements Hello {
        private String name;

        public HelloTarget(String name) {
            this.name = name;
        }


        @Override
        public String hello() {
            return "Hello " + name;
        }
    }

    // InvocationHandler 는 타겟이 필요 (주입을 받아야..)
    // 나중에 나오는 MethodInvocation 은 그 안에 이미 타겟이 존재..?
    public class UppercaseHandler implements InvocationHandler {
        Hello target;

        public UppercaseHandler(Hello target) {
            this.target = target;
        }

        // 여기서 proxy 는 왜 전달해줄까??
        @Override
        public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
            log.debug("called");

            // 여기서 target 을 전달해주어야함...
            // proxy 를 전달해주면.. 무한루프를 돌게됨
            //
            // proxy 로 했을 때... 메모리가 모자르다는 애러가 발생
            // 그리고 테스트가 끝나지 않았음
            // 로그를 출력했더니 무한루프였음
            Object ret = method.invoke(target, args);
            if (ret instanceof String) {
                return ((String) ret).toUpperCase();
            }
            return ret;
        }
    }
}
