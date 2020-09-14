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

import db.DataBase;
import model.User;
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
            ServletRequest servletRequest = new ServletRequest(br);
            List<Class<? extends Handlers>> controllers = Arrays.asList(UserController.class, IndexController.class);
            HandlerMapping handlerMapping = new HandlerMapping(controllers);
            Method handler = handlerMapping.mapping(servletRequest);
            HandlerAdaptor handlerAdaptor = new HandlerAdaptor();
            HttpMessageConverter messageConverter = new DefaultHttpMessageConverter();
            ServletResponse servletResponse = handlerAdaptor.invoke(handler, servletRequest, messageConverter);

            User user = convert(User.class, requestBody.getAttribute());
            DataBase.addUser(user);
            DataOutputStream dos = new DataOutputStream(out);
            // response302Header(dos);
            // String contentType = requestHeader.getHeader("Accept").split(",")[0];
            servletResponse.createResponse(dos);
            // byte[] body = FileIoUtils.loadFileFromClasspath(requestHeader.getPath());
            // response200Header(dos, body.length, contentType);
            // responseBody(dos, body);
        } catch (IOException e) {
            logger.error(e.getMessage());
        }
    }

    private void response200Header(DataOutputStream dos, int lengthOfBodyContent, String type) {
        try {
            dos.writeBytes("HTTP/1.1 200 OK \r\n");
            dos.writeBytes("Content-Type: " + type + ";charset=utf-8\r\n");
            dos.writeBytes("Content-Length: " + lengthOfBodyContent + "\r\n");
            dos.writeBytes("\r\n");
        } catch (IOException e) {
            logger.error(e.getMessage());
        }
    }

    private void response302Header(DataOutputStream dos) {
        try {
            dos.writeBytes("HTTP/1.1 302 Found \r\n");
            dos.writeBytes("Location: http://localhost:8080/index.html\r\n");
            dos.writeBytes("\r\n");
        } catch (IOException e) {
            logger.error(e.getMessage());
        }
    }

    private void responseBody(DataOutputStream dos, byte[] body) {
        try {
            dos.write(body, 0, body.length);
            dos.flush();
        } catch (IOException e) {
            logger.error(e.getMessage());
        }
    }
}
