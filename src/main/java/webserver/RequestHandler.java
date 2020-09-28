package webserver;

import java.io.DataOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import webserver.http.request.HttpRequest;
import webserver.http.request.HttpRequestFactory;
import webserver.http.response.HttpResponse;

public class RequestHandler implements Runnable {
    private static final Logger logger = LoggerFactory.getLogger(RequestHandler.class);

    private Socket connection;

    public RequestHandler(Socket connectionSocket) {
        this.connection = connectionSocket;
    }

    @Override
    public void run() {
        logger.debug("New Client Connect! Connected IP : {}, Port : {}", connection.getInetAddress(),
                connection.getPort());

        try (InputStream in = connection.getInputStream(); OutputStream out = connection.getOutputStream()) {
            DataOutputStream dos = new DataOutputStream(out);
            HttpRequest httpRequest = HttpRequestFactory.create(in);
            HttpResponse httpResponse = HttpResponse.ofVersion(httpRequest.getHttpVersion());
            RequestMapper.execute(httpRequest, httpResponse);
            httpResponse.write(dos);
        } catch (Exception e) {
            logger.error(e.getMessage());
        }
    }
}
