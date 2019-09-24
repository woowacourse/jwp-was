package webserver;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import webserver.Controller.Controller;
import webserver.Controller.MainController;
import webserver.Controller.SignUpController;
import webserver.Controller.UserController;
import webserver.request.HttpRequest;
import webserver.response.HttpResponse;

import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.util.HashMap;
import java.util.Map;

public class RequestHandler implements Runnable {
    private static final Logger logger = LoggerFactory.getLogger(RequestHandler.class);
    private static Map<String, Controller> urlMapper = new HashMap<>();

    static {
        urlMapper.put("/index.html", new MainController());
        urlMapper.put("/user/form.html", new UserController());
        urlMapper.put("/user/create", new SignUpController());
    }

    private Socket connection;

    public RequestHandler(Socket connectionSocket) {
        this.connection = connectionSocket;
    }

    public void run() {
        logger.debug("New Client Connect! Connected IP : {}, Port : {}", connection.getInetAddress(),
                connection.getPort());

        try (InputStream in = connection.getInputStream(); OutputStream out = connection.getOutputStream()) {
            DataOutputStream dos = new DataOutputStream(out);

            HttpRequest httpRequest = HttpRequest.of(in);
            HttpResponse httpResponse = new HttpResponse();

            urlMapper.get(httpRequest.getSource()).service(httpRequest, httpResponse);
            httpResponse.send(dos);

        } catch (IOException e) {
            logger.error(e.getMessage());
        }
    }
}
