package webserver;

import controller.Controller;
import controller.ControllerManager;
import controller.ControllerMapperInterface;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import request.HttpRequest;
import request.RequestCookies;
import response.HttpResponse;
import session.Session;
import session.SessionStorage;

public class RequestHandler implements Runnable {

    private static final Logger logger = LoggerFactory.getLogger(RequestHandler.class);

    private final ControllerMapperInterface controllerMapper;
    private final ControllerManager controllerManager;
    private final Socket connection;

    RequestHandler(ControllerMapperInterface controllerMapper, Socket connectionSocket) {
        this.controllerMapper = controllerMapper;
        this.controllerManager = new ControllerManager(controllerMapper.readAllControllerInfo());

        this.connection = connectionSocket;
    }

    @Override
    public void run() {
        logger.debug("New Client Connect! Connected IP : {}, Port : {}",
            connection.getInetAddress(), connection.getPort());

        try (
            InputStream in = connection.getInputStream();
            OutputStream out = connection.getOutputStream()
        ) {
            HttpRequest httpRequest = HttpRequest.readHttpRequest(in);
            logger.info("Receive HttpRequest\n{}", httpRequest.toString());

            Session session = findOrCreateSession(httpRequest);

            Controller controller = findController(httpRequest);
            HttpResponse response = controller.service(httpRequest, session);

            SessionStorage.removeSessionIfEmpty(session.getId());

            logger.info("HttpResponse to send\n{}", response.toString());
            DataOutputStream dos = new DataOutputStream(out);
            writeResponseOnOutputStream(dos, response);
        } catch (IOException e) {
            logger.error(e.getMessage());
        }
    }

    private Controller findController(HttpRequest httpRequest) {
        Class<?> controllerClass = controllerMapper.findController(httpRequest);
        return controllerManager.get(controllerClass);
    }

    private Session findOrCreateSession(HttpRequest httpRequest) {
        try {
            return findSession(httpRequest);
        } catch (IllegalArgumentException e) {    // 세션을 찾을 수 없는 경우
            return SessionStorage.createSession();
        }
    }

    private Session findSession(HttpRequest httpRequest) {
        String requestCookieHeaderFormat = httpRequest.getHeader("Cookie");
        RequestCookies cookies = RequestCookies.from(requestCookieHeaderFormat);

        String sessionId = cookies.getValue("sessionId");
        return SessionStorage.findSession(sessionId);
    }

    private void writeResponseOnOutputStream(DataOutputStream dos, HttpResponse response) {
        try {
            dos.writeBytes(response.buildHeader());

            byte[] body = response.getBody();
            dos.write(body, 0, body.length);
            dos.flush();
        } catch (IOException e) {
            logger.error(e.getMessage());
        }
    }
}
