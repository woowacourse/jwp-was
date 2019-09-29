package dev.luffy.webserver;

import dev.luffy.annotation.Controller;
import dev.luffy.annotation.RequestMapping;
import dev.luffy.http.RequestMapper;
import org.reflections.Reflections;
import org.reflections.scanners.MethodAnnotationsScanner;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Set;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.function.Consumer;
import java.util.function.Function;

public class WebServer {
    private static final Logger logger = LoggerFactory.getLogger(WebServer.class);

    private static final int DEFAULT_PORT = 8080;
    private static final int THREAD_POOL = 100;
    private static final String PACKAGE_NAME = "dev.luffy";
    private static final String ADD_METHOD = "add";

    public static void main(String args[]) throws Exception {
        scanRequestMapping();

        int port = 0;
        if (args == null || args.length == 0) {
            port = DEFAULT_PORT;
        } else {
            port = Integer.parseInt(args[0]);
        }

        ExecutorService executor = Executors.newFixedThreadPool(THREAD_POOL);
        // 서버소켓을 생성한다. 웹서버는 기본적으로 8080번 포트를 사용한다.
        try (ServerSocket listenSocket = new ServerSocket(port)) {
            logger.info("Web Application Server started {} port.", port);

            // 클라이언트가 연결될때까지 대기한다.
            Socket connection;
            while ((connection = listenSocket.accept()) != null) {
                Socket finalConnection = connection;
                executor.execute(() -> {
                    String threadName = Thread.currentThread().getName();
                    logger.debug("Thread - {}", threadName);
                    Thread thread = new Thread(new RequestHandler(finalConnection));
                    thread.start();
                });
            }
        }
    }

    private static void scanRequestMapping() throws NoSuchMethodException {
        Reflections reflections = new Reflections(PACKAGE_NAME);
        Set<Class<?>> controllerClasses = reflections.getTypesAnnotatedWith(Controller.class);
        Method requestMapAddMethod = RequestMapper.class.getMethod(ADD_METHOD, dev.luffy.http.RequestMapping.class, Method.class);
        controllerClasses
                .stream()
                .map(requestMappingAnnotatedMethodScannerMapper())
                .forEach(addRequestMapper(requestMapAddMethod));
    }

    private static Function<Class<?>, Set<?>> requestMappingAnnotatedMethodScannerMapper() {
        return aClass ->
                new Reflections(aClass.getName(), new MethodAnnotationsScanner()).getMethodsAnnotatedWith(RequestMapping.class);
    }

    private static Consumer<Set<?>> addRequestMapper(Method requestMapAddMethod) {
        return objects -> ((Set<Method>) objects)
                .forEach(method -> {
                    try {
                        RequestMapping annotation = method.getAnnotation(RequestMapping.class);
                        dev.luffy.http.RequestMapping requestMapping = new dev.luffy.http.RequestMapping(annotation.method(), annotation.path());
                        requestMapAddMethod.invoke(null, requestMapping, method);
                    } catch (IllegalAccessException | InvocationTargetException e) {
                        e.printStackTrace();
                    }
                });
    }
}
