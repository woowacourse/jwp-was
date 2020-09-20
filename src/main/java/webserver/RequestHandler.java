package webserver;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.lang.reflect.Method;
import java.net.Socket;
import java.util.Arrays;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import webserver.controller.Handlers;
import webserver.controller.IndexController;
import webserver.controller.UserController;

public class RequestHandler implements Runnable {
    private static final Logger logger = LoggerFactory.getLogger(RequestHandler.class);

    private Socket connection;

    public RequestHandler(Socket connectionSocket) {
        this.connection = connectionSocket;
    }

    public void run() {
        logger.debug("New Client Connect! Connected IP : {}, Port : {}", connection.getInetAddress(),
            connection.getPort());
        try (InputStream in = connection.getInputStream();
             OutputStream out = connection.getOutputStream();
             InputStreamReader ir = new InputStreamReader(in);
             BufferedReader br = new BufferedReader(ir);
             DataOutputStream dos = new DataOutputStream(out))
        {
            ServletRequest servletRequest = new ServletRequest(br);
            ViewResolver defaultViewResolver = new DefaultViewResolver();
            View view = defaultViewResolver.resolve(servletRequest);

            if (view.isNotEmpty()) {
                final ServletResponse servletResponse = new ServletResponse(ServletResponse.StatusCode.OK);
                servletResponse.sendResponse(dos, servletRequest, view);
                return;
            }

            List<Class<? extends Handlers>> controllers = Arrays.asList(UserController.class, IndexController.class);
            HandlerMapping defaultHandlerMapping = new DefaultHandlerMapping(controllers);
            Method handler = defaultHandlerMapping.mapping(servletRequest);
            HandlerAdaptor defaultHandlerAdaptor = new DefaultHandlerAdaptor();
            HttpMessageConverter converter = new DefaultHttpMessageConverter();
            ServletResponse servletResponse = defaultHandlerAdaptor.invoke(handler, servletRequest, converter);
            servletResponse.sendResponse(dos, servletRequest);
        } catch (IOException e) {
            logger.error(e.getMessage());
        }
    }
}
