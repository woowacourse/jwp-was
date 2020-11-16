package webserver;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.Socket;
import java.net.URISyntaxException;
import java.nio.charset.StandardCharsets;
import java.util.Map;
import java.util.Objects;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import controller.Controller;
import service.session.SessionService;
import session.HttpSession;

public class RequestHandler implements Runnable {
    private static final Logger logger = LoggerFactory.getLogger(RequestHandler.class);

    private final Socket connection;
    private final Context context;
    private final Map<String, Controller> controllers;
    private final SessionService sessionService;

    public RequestHandler(Socket connectionSocket) {
        this.connection = connectionSocket;
        this.context = Context.getInstance();
        controllers = context.createControllerBifurcation();
        sessionService = SessionService.getInstance();
    }

    public void run() {
        logger.debug("New Client Connect! Connected IP : {}, Port : {}", connection.getInetAddress(),
            connection.getPort());

        try (InputStream in = connection.getInputStream(); OutputStream out = connection.getOutputStream()) {
            BufferedReader br = new BufferedReader(new InputStreamReader(in, StandardCharsets.UTF_8));
            HttpRequest httpRequest = new HttpRequest(br);
            if (Objects.isNull(httpRequest.getSessionId())) {
                sessionService.add(httpRequest.getHttpSession());
            }
            if (Objects.nonNull(httpRequest.getSessionId())) {
                HttpSession httpSession = sessionService.findById(httpRequest.getSessionId());
                httpRequest.setHttpSession(httpSession);
            }
            printHeader(httpRequest);
            printParameter(httpRequest);
            Controller controller = controllers.getOrDefault(httpRequest.getPath(), controllers.get("file"));
            controller.service(httpRequest, new HttpResponse(out));
            sessionService.update(httpRequest.getHttpSession());
        } catch (IOException | URISyntaxException e) {
            logger.error(e.getMessage());
        }
    }

    private void printHeader(HttpRequest httpRequest) {
        logger.debug("header : {}",
            String.format("%s %s HTTP/1.1", httpRequest.getHttpMethod().name(), httpRequest.getPath()));
        httpRequest.getHeader()
            .forEach((key, value) -> logger.debug("header : {}", String.format("%s: %s", key, value)));
    }

    private void printParameter(HttpRequest httpRequest) {
        httpRequest.getParameter()
            .forEach((key, value) -> logger.debug("body : {}", String.format("%s = %s", key, value)));
    }
}
