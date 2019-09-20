package webserver;

import controller.Controller;
import controller.CreateUserController;
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
import java.net.URISyntaxException;
import java.util.HashMap;
import java.util.Map;

public class RequestHandler implements Runnable {
    private static final Logger logger = LoggerFactory.getLogger(RequestHandler.class);

    private static Map<String, Controller> controllers = new HashMap<>();

    static {
        controllers.put("/user/create", new CreateUserController());
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

            String url = request.getPath();
            String extension = url.substring(url.lastIndexOf(".") + 1);

            processResponse(dos, request, url, extension);

        } catch (IOException | URISyntaxException e) {
            logger.error(e.getMessage());
        }
    }

    private void processResponse(DataOutputStream dos, Request request, String url, String extension) throws URISyntaxException, IOException {
        Response response = new Response(dos);

        if (!extension.startsWith("/")) {
            String classPath = getClassPath(url, extension);
            response.responseResource(classPath);
            return;
        }

        Controller controller = controllers.get(request.getPath());
        controller.service(request, response);
    }

    private String getClassPath(String url, String extension) {
        if ("html".equals(extension)) {
            return "./templates" + url;
        }

        return "./static" + url;
    }
}

