package webserver;

import http.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;

public class RequestHandler implements Runnable {
    private static final Logger LOGGER = LoggerFactory.getLogger(RequestHandler.class);
    private static final Router ROUTER = Router.getInstance();

    private Socket connection;

    public RequestHandler(Socket connectionSocket) {
        this.connection = connectionSocket;
    }

    public void run() {
        LOGGER.debug("New Client Connect! Connected IP : {}, Port : {}", connection.getInetAddress(),
                connection.getPort());

        try (InputStream in = connection.getInputStream(); OutputStream out = connection.getOutputStream()) {
            // TODO 사용자 요청에 대한 처리는 이 곳에 구현하면 된다.
            HttpRequest httpRequest = new HttpRequest(in);
            HttpResponse httpResponse = new HttpResponse(out);

            // static files
            ResourceHandler handler = StaticResourceHandler.getInstance();
            if (handler.handle(httpRequest, httpResponse)) {
                return;
            }

            // servlet
            // Router Call -> Servlet
            // Servlet Call

            Servlet servlet = ROUTER.getServlet(httpRequest.getPath());
            if (servlet != null) {
                servlet.service(httpRequest, httpResponse);
                return;
            }

            // cannot serve
            // TODO: 404 NOT FOUND
        } catch (IOException e) {
            LOGGER.error(e.getMessage());
        }
    }
}
