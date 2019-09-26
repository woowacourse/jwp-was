package webserver;

import http.HttpResponse;
import http.request.HttpRequest;
import http.request.HttpRequestFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import webserver.controller.Controller;
import webserver.controller.ControllerFinder;

import java.io.*;
import java.net.Socket;
import java.net.URISyntaxException;

public class RequestHandler implements Runnable {
    private static final Logger logger = LoggerFactory.getLogger(RequestHandler.class);
    private final ControllerFinder controllerFinder;

    private Socket connection;

    public RequestHandler(Socket connectionSocket, ControllerFinder controllerFinder) {
        this.connection = connectionSocket;
        this.controllerFinder = controllerFinder;
    }

    public void run() {
        logger.debug("New Client Connect! Connected IP : {}, Port : {}", connection.getInetAddress(),
                connection.getPort());

        try (InputStream in = connection.getInputStream(); OutputStream out = connection.getOutputStream()) {
            BufferedReader bufferedReader = getBufferedReader(in);

            HttpRequest httpRequest = HttpRequestFactory.create(bufferedReader);
            HttpResponse httpResponse = new HttpResponse(new DataOutputStream(out));

            // HttpRequest가 정적 파일을 요구하는지 확인 (if)
            // HttpRequestServlet 처리할 것(else)
            // 3. 다 처리하면 웹서버가 할 일을 해줄 것 (웹서버는 응답을 한다. 세션도 지정한다?)
            Controller controller = controllerFinder.find(httpRequest);
            controller.service(httpRequest, httpResponse);
        } catch (IOException | URISyntaxException e) {
            logger.error(e.getMessage());
        }
    }

    private BufferedReader getBufferedReader(InputStream inputStream) {
        InputStreamReader inputStreamReader = new InputStreamReader(inputStream);
        return new BufferedReader(inputStreamReader);
    }
}
