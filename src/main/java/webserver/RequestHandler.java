package webserver;

import http.request.HttpRequest;
import http.response.HttpResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;

public class RequestHandler implements Runnable {
    private static final Logger logger = LoggerFactory.getLogger(RequestHandler.class);
    private Socket connection;

    public RequestHandler(Socket connectionSocket) {
        this.connection = connectionSocket;
    }

    public void run() {
        logger.debug("New Client Connect! Connected IP : {}, Port : {}", connection.getInetAddress(),
                connection.getPort());

        try (InputStream in = connection.getInputStream(); OutputStream out = connection.getOutputStream()) {
            HttpRequest httpRequest = RequestFactory.createHttpRequest(in);
            DataOutputStream dos = new DataOutputStream(out);

            HttpResponse httpResponse = HttpResponse.of(dos);

            Router.route(httpRequest, httpResponse);

            httpResponse.forward();

        } catch (IOException e) {
            logger.error(e.getMessage());
            // TODO: 2019-09-20 400에러
        }
    }
}
