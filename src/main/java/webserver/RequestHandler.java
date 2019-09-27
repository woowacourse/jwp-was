package webserver;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import webserver.dispatcher.RequestDispatcher;
import webserver.http.request.HttpRequest;
import webserver.http.response.HttpResponse;
import webserver.http.response.HttpVersion;
import webserver.parser.HttpRequestParser;

import java.io.*;
import java.net.Socket;
import java.nio.charset.StandardCharsets;

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
            HttpRequest httpRequest = HttpRequestParser.parse(new BufferedReader(new InputStreamReader(in, StandardCharsets.UTF_8)));
            HttpResponse httpResponse = new HttpResponse(new DataOutputStream(out), httpRequest.getHttpVersion());
            RequestDispatcher requestDispatcher = new RequestDispatcher(httpResponse);
            requestDispatcher.dispatch(httpRequest);
        } catch (IOException e) {
            logger.error(e.getMessage());
        }
    }
}
