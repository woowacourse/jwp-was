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

import utils.FileIoUtils;
import webserver.controller.Handlers;
import webserver.controller.IndexController;
import webserver.controller.UserController;

public class DispatcherServlet implements Runnable {
    private static final Logger logger = LoggerFactory.getLogger(DispatcherServlet.class);

    private Socket connection;

    public DispatcherServlet(Socket connectionSocket) {
        this.connection = connectionSocket;
    }

    public void run() {
        logger.debug("New Client Connect! Connected IP : {}, Port : {}", connection.getInetAddress(),
            connection.getPort());
        try (InputStream in = connection.getInputStream(); OutputStream out = connection.getOutputStream();
             InputStreamReader ir = new InputStreamReader(in); BufferedReader br = new BufferedReader(ir)) {
            DataOutputStream dos = new DataOutputStream(out);

            ServletRequest servletRequest = new ServletRequest(br);
            byte[] bytes = FileIoUtils.loadFileFromClasspath(servletRequest.getPath());

            try {
                dos.writeBytes("HTTP/1.1 200 OK \r\n");
                dos.writeBytes("Content-Type: text/html;charset=utf-8\r\n");
                dos.writeBytes("Content-Length: " + bytes.length + "\r\n");
                dos.writeBytes("\r\n");
            } catch (IOException e) {
                logger.error(e.getMessage());
            }

            dos.write(bytes, 0, bytes.length);
            dos.flush();

            List<Class<? extends Handlers>> controllers = Arrays.asList(UserController.class, IndexController.class);
            HandlerMapping handlerMapping = new HandlerMapping(controllers);
            Method handler = handlerMapping.mapping(servletRequest);
            HandlerAdaptor handlerAdaptor = new HandlerAdaptor();
            HttpMessageConverter messageConverter = new DefaultHttpMessageConverter();
            ServletResponse servletResponse = handlerAdaptor.invoke(handler, servletRequest, messageConverter);
            servletResponse.createResponse(dos, servletRequest);
        } catch (IOException e) {
            logger.error(e.getMessage());
        }
    }
}
