package webserver;

import controller.Controller;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;

public class RequestHandler implements Runnable {
    private static final Logger logger = LoggerFactory.getLogger(RequestHandler.class);
    private static final String DIVISION_EXTENSION = ".";
    private static final String PREFIX_SLASH = "/";
    private static final String HTML = "html";
    private static final String TEMPLATES = "./templates";
    private static final String STATIC = "./static";
    private static final int NEXT_INT = 1;

    private Socket connection;
    private ControllerHandler controllerHandler;

    public RequestHandler(Socket connectionSocket) {
        this.connection = connectionSocket;
        this.controllerHandler = new ControllerHandler();
    }

    public void run() {
        logger.debug("New Client Connect! Connected IP : {}, Port : {}", connection.getInetAddress(),
                connection.getPort());

        try (InputStream in = connection.getInputStream(); OutputStream out = connection.getOutputStream()) {
            DataOutputStream dos = new DataOutputStream(out);
            RequestParser requestParser = new RequestParser(in);
            HttpRequest request = new HttpRequest(requestParser.getHeaderInfo(), requestParser.getParameter());

            String url = request.getPath();
            String extension = url.substring(url.lastIndexOf(DIVISION_EXTENSION) + NEXT_INT);

            HttpResponse httpResponse = new HttpResponse(dos);

            if (!extension.startsWith(PREFIX_SLASH)) {
                String classPath = getClassPath(url, extension);
                httpResponse.setHttpStatus(HttpStatus.OK);
                httpResponse.response(classPath);
                return;
            }

            Controller controller = controllerHandler.getController(request.getPath());
            controller.service(request, httpResponse);

        } catch (IOException e) {
            logger.error(e.getMessage());
        }
    }

    private String getClassPath(String url, String extension) {
        if (HTML.equals(extension)) {
            return TEMPLATES + url;
        }

        return STATIC + url;
    }
}

