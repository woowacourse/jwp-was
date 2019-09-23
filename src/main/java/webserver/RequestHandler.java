package webserver;

import http.HttpResponse;
import http.controller.RequestMapping;
import http.request.HttpRequest;
import http.request.HttpRequestFactory;
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
            HttpRequest request = HttpRequestFactory.createHttpRequest(in);
            logger.debug("Http Request\n{}", request);
            HttpResponse response = new HttpResponse();
            DataOutputStream dos = new DataOutputStream(out);

            RequestMapping.getController(request.getPath())
                    .service(request, response);
            dos.write(response.convert().getBytes());
            dos.flush();
            logger.debug("Http Response\n{}", response);
        } catch (IOException e) {
            logger.error(e.getMessage());
        }
    }
}
