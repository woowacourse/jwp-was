package webserver;

import controller.Controller;
import controller.ControllerHandler;
import http.request.Request;
import http.request.RequestParser;
import http.response.Response;
import http.response.ResponseWriter;
import http.support.HttpStatus;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import utils.ExtractInformationUtils;

import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.util.Optional;

public class RequestHandler implements Runnable {
    private static final Logger logger = LoggerFactory.getLogger(RequestHandler.class);
    private static final String PREFIX_SLASH = "/";
    private static final String HTML = "html";
    private static final String TEMPLATES = "./templates";
    private static final String STATIC = "./static";

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
            Request request = new Request(requestParser.getHeaderInfo(), requestParser.getParameter());

            String url = request.getPath();
            String extension = ExtractInformationUtils.extractExtension(url);

            Response response = new Response();
            ResponseWriter responseWriter = new ResponseWriter();

            setResponse(request, url, extension, response);
            responseWriter.send(dos, response);
        } catch (IOException e) {
            logger.error(e.getMessage());
        }
    }

    private void setResponse(Request request, String url, String extension, Response response) {
        if (!extension.startsWith(PREFIX_SLASH)) {
            String classPath = getClassPath(url, extension);
            response.forward(classPath, HttpStatus.OK);
            return;
        }

        Optional<Controller> controller = controllerHandler.getController(request.getPath());
        if (controller.isPresent()) {
            controller.get().service(request, response);
            return;
        }

        response.notfound();
    }

    private String getClassPath(String url, String extension) {
        if (HTML.equals(extension)) {
            return TEMPLATES + url;
        }

        return STATIC + url;
    }
}

