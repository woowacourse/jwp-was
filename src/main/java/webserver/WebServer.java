package webserver;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Set;

import org.reflections.Reflections;
import org.reflections.scanners.MethodAnnotationsScanner;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import annotation.Controller;
import http.request.MappedRequest;

public class WebServer {
    private static final Logger logger = LoggerFactory.getLogger(WebServer.class);
    private static final int DEFAULT_PORT = 8080;

    public static void main(String args[]) throws Exception {

        Reflections reflections = new Reflections("controller");
        Set<Class<?>> controllerClasses = reflections.getTypesAnnotatedWith(Controller.class);
        Method addMethod = RequestMapper.class.getMethod("add", MappedRequest.class, Method.class);

        controllerClasses.stream()
            .map(aClass -> new Reflections(aClass.getName(), new MethodAnnotationsScanner()).getMethodsAnnotatedWith(
                annotation.RequestMapping.class))
            .forEach(classes -> {
                classes
                    .forEach(method -> {
                        annotation.RequestMapping annotation = method.getAnnotation(annotation.RequestMapping.class);
                        MappedRequest mappedRequest = new MappedRequest(annotation.method(), annotation.path());
                        try {
                            addMethod.invoke(null, mappedRequest, method);
                        } catch (IllegalAccessException | InvocationTargetException e) {
                            e.printStackTrace();
                        }
                    });
            });

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
}
