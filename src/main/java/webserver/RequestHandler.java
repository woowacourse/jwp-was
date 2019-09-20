package webserver;

import controller.Controller;
import controller.CreateUserController;
import controller.IndexController;
import model.Request;
import model.RequestParser;
import model.Response;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.util.HashMap;
import java.util.Map;

public class RequestHandler implements Runnable {
    private static final Logger logger = LoggerFactory.getLogger(RequestHandler.class);

    private static Map<String, Controller> controllers = new HashMap<>();

    static {
        controllers.put("GET", new IndexController());
        controllers.put("POST", new CreateUserController());
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
            RequestParser requestParser = new RequestParser(in);
            Request request = new Request(requestParser.getHeaderInfo(), requestParser.getParameter());
            Response response = new Response(dos);

            Controller controller = controllers.get(request.getMethod());
            controller.service(request, response);
        } catch (IOException e) {
            logger.error(e.getMessage());
        }
    }

}

