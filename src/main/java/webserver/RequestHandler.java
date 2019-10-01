package webserver;

import controller.Controller;
import controller.ControllerHandler;
import http.Cookie;
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
    private static final String JSESSION = "JSESSION";

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
            Response response = new Response();
            ResponseWriter responseWriter = new ResponseWriter();

            setResponse(request, response);

            Optional<Controller> pathController = controllerHandler.getController(request.getPath());
            pathController.ifPresent(controller -> controller.service(request, response));

            responseWriter.send(dos, response);
        } catch (IOException e) {
            logger.error(e.getMessage());
        }
    }

    private void setResponse(Request request, Response response) {
        String url = request.getPath();
        String extension = ExtractInformationUtils.extractExtension(url);
        setSession(request, response, extension);

        if (isFile(extension)) {
            String classPath = getClassPath(url, extension);
            response.forward(classPath, HttpStatus.OK);
            return;
        }

        if (!isFile(extension) && !extension.startsWith(PREFIX_SLASH)) {
            response.notfound();
        }
    }

    private void setSession(Request request, Response response, String extension) {
        if (isNotHtmlOrFile(extension)) {
            return;
        }

        request.setSession();

        if (request.notContainSession() || request.mismatchSessionId()) {
            response.addCookie(new Cookie(JSESSION, request.getSessionId()));
        }
    }

    private boolean isNotHtmlOrFile(String extension) {
        return !(extension.equals(HTML) || extension.startsWith(PREFIX_SLASH));
    }

    private boolean isFile(String extension) {
        return !extension.startsWith(PREFIX_SLASH);
    }

    private String getClassPath(String url, String extension) {
        if (HTML.equals(extension)) {
            return TEMPLATES + url;
        }

        return STATIC + url;
    }
}

