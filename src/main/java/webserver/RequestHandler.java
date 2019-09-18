package webserver;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.net.URISyntaxException;

public class RequestHandler implements Runnable {
    private static final Logger logger = LoggerFactory.getLogger(RequestHandler.class);

    private Socket connection;

    public RequestHandler(Socket connectionSocket) {
        this.connection = connectionSocket;
    }

    public void run() {
        logger.debug("New Client Connect! Connected IP : {}, Port : {}", connection.getInetAddress(),
                connection.getPort());

        try (InputStream in = connection.getInputStream();
             OutputStream out = connection.getOutputStream();
             HttpRequest httpRequest = new HttpRequest(in);
             HttpResponse httpResponse = new HttpResponse(out)) {
            // TODO 사용자 요청에 대한 처리는 이 곳에 구현하면 된다.
            ControllerContainer.service(httpRequest, httpResponse);



        } catch (IOException | URISyntaxException e) {
            logger.error(e.getMessage());
        }
    }
}
