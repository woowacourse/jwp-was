package webserver;

import controller.Controller;
import controller.ControllerHandler;
import http.Cookie;
import http.SessionStore;
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

        Optional<Controller> controller = controllerHandler.getController(request.getPath());
        if (controller.isPresent()) {
            controller.get().service(request, response);
            return;
        }

        response.notfound();
    }

    private void setSession(Request request, Response response, String extension) {
        Cookie requestCookie = request.getCookie(JSESSION);

        if (!isHtmlOrNotFile(extension)) {
            return;
        }

        if (request.getCookie(JSESSION) != null) {
            request.setSession(SessionStore.getSession(request.getCookie(JSESSION).getValue()));

            if (SessionStore.getSession(request.getCookie(JSESSION).getValue()).equals(requestCookie.getValue())) {
                response.addCookie(new Cookie(JSESSION, request.getCookie(JSESSION).getValue()));
            }
        }

        if (request.getCookie(JSESSION) == null) {
            request.setSession(SessionStore.getSession(request.getCookie(JSESSION).getValue()));
            response.addCookie(new Cookie(JSESSION, request.getCookie(JSESSION).getValue()));
        }
    }

    private boolean isHtmlOrNotFile(String extension) {
        return extension.equals(HTML) || extension.startsWith(PREFIX_SLASH);
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

