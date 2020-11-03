package webserver;

import http.AbstractServlet;
import http.ControllerMapper;
import org.reflections.Reflections;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import webserver.annotation.Controller;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Set;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class WebServer {
    private static final Logger logger = LoggerFactory.getLogger(WebServer.class);
    public static final String CONTROLLER_PACKAGE_PATH = "controller";
    private static final int DEFAULT_PORT = 8080;

    public static void main(String args[]) throws Exception {
        int port = 0;
        if (args == null || args.length == 0) {
            port = DEFAULT_PORT;
        } else {
            port = Integer.parseInt(args[0]);
        }
        initControllerMapper();

        try (ServerSocket listenSocket = new ServerSocket(port)) {
            logger.info("Web Application Server started {} port.", port);

            Socket connection;
            ExecutorService es = Executors.newFixedThreadPool(6);
            while ((connection = listenSocket.accept()) != null) {
                ControllerMapper controllerMapper = ControllerMapper.getInstance();
                es.execute(new RequestHandler(connection, controllerMapper));
            }
        }
    }

    private static void initControllerMapper() throws IllegalAccessException, InvocationTargetException, InstantiationException {
        Reflections reflections = new Reflections(CONTROLLER_PACKAGE_PATH);
        Set<Class<?>> controllerClasses = reflections.getTypesAnnotatedWith(Controller.class);
        for (Class<?> controllerClass : controllerClasses) {
            Constructor<?> constructor = controllerClass.getDeclaredConstructors()[0];
            AbstractServlet controller = (AbstractServlet) constructor.newInstance();
            logger.debug("add controller to mapper: {}", controller.getClass().getName());
            ControllerMapper.getInstance().addController(controller);
        }
    }
}
