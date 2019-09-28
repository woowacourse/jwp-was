package webserver;

import file.FileContainer;
import http.request.HttpRequest;
import http.request.support.HttpRequestFactory;
import http.response.HttpResponse;
import http.session.HttpSession;
import http.session.support.SessionManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import servlet.ServletContainer;
import servlet.controller.ControllerFinder;

import java.io.*;
import java.net.Socket;
import java.net.URISyntaxException;

public class RequestHandler implements Runnable {
    private static final Logger logger = LoggerFactory.getLogger(RequestHandler.class);
    private final ControllerFinder controllerFinder;
    private final SessionManager sessionManager;

    private Socket connection;

    public RequestHandler(Socket connectionSocket, ControllerFinder controllerFinder, SessionManager sessionManager) {
        this.connection = connectionSocket;
        this.controllerFinder = controllerFinder;
        this.sessionManager = sessionManager;
    }

    public void run() {
        logger.debug("New Client Connect! Connected IP : {}, Port : {}", connection.getInetAddress(),
                connection.getPort());

        FileContainer fileContainer = new FileContainer();
        ServletContainer servletContainer = new ServletContainer(controllerFinder);

        try (InputStream in = connection.getInputStream(); OutputStream out = connection.getOutputStream()) {
            BufferedReader bufferedReader = getBufferedReader(in);

            HttpRequest httpRequest = HttpRequestFactory.create(bufferedReader, sessionManager);
            HttpResponse httpResponse = new HttpResponse(new DataOutputStream(out));
            httpResponse.addCookie(SessionManager.SESSION_NAME, httpRequest.getSessionId());

            if (!fileContainer.process(httpRequest, httpResponse)) {
                servletContainer.process(httpRequest, httpResponse);
            }
        } catch (IOException | URISyntaxException e) {
            logger.error(e.getMessage());
        }
    }

    private BufferedReader getBufferedReader(InputStream inputStream) {
        InputStreamReader inputStreamReader = new InputStreamReader(inputStream);
        return new BufferedReader(inputStreamReader);
    }
}
