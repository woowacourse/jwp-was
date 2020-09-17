package webserver;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Set;
import java.util.function.Consumer;
import java.util.function.Function;

import org.reflections.Reflections;
import org.reflections.scanners.MethodAnnotationsScanner;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import webserver.annotation.Controller;
import webserver.annotation.RequestMapping;
import webserver.http.request.MappedRequest;

public class WebServer {
    private static final Logger logger = LoggerFactory.getLogger(WebServer.class);
    private static final int DEFAULT_PORT = 8080;

    public static void main(String args[]) throws Exception {
        scanRequestMappingAnnotatedMethod();

        int port = 0;
        if (args == null || args.length == 0) {
            port = DEFAULT_PORT;
        } else {
            port = Integer.parseInt(args[0]);
        }

        // 서버소켓을 생성한다. 웹서버는 기본적으로 8080번 포트를 사용한다.
        try (ServerSocket listenSocket = new ServerSocket(port)) {
            logger.info("Web Application Server started {} port.", port);

            // 클라이언트가 연결될때까지 대기한다.
            Socket connection;
            while ((connection = listenSocket.accept()) != null) {
                Thread thread = new Thread(new RequestHandler(connection));
                thread.start();
            }
        }
    }

    private static void scanRequestMappingAnnotatedMethod() throws NoSuchMethodException {
        Reflections reflections = new Reflections("application/controller");
        Set<Class<?>> controllerAnnotatedClasses = reflections.getTypesAnnotatedWith(Controller.class);

        Method addToRequestMapper = RequestMapper.class.getMethod("add", MappedRequest.class, Method.class);

        controllerAnnotatedClasses.stream()
            .map(findRequestMappingAnnotatedMethods())
            .forEach(applyAnnotatedMethodsTo(addToRequestMapper));
    }

    private static Function<Class<?>, Set<Method>> findRequestMappingAnnotatedMethods() {
        return aClass ->
            new Reflections(aClass.getName(), new MethodAnnotationsScanner())
                .getMethodsAnnotatedWith(RequestMapping.class);
    }

    private static Consumer<Set<Method>> applyAnnotatedMethodsTo(Method addToRequestMapper) {
        return classes -> {
            classes.forEach(addAnnotatedMethodToRequestMapper(addToRequestMapper));
        };
    }

    private static Consumer<Method> addAnnotatedMethodToRequestMapper(Method addToRequestMapper) {
        return annotatedMethod -> {
            RequestMapping annotation = annotatedMethod.getAnnotation(RequestMapping.class);
            MappedRequest mappedRequest = new MappedRequest(annotation.method(), annotation.path());
            try {
                addToRequestMapper.invoke(null, mappedRequest, annotatedMethod);
            } catch (IllegalAccessException | InvocationTargetException e) {
                e.printStackTrace();
            }
        };
    }
}