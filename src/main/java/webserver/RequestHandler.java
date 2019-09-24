package webserver;

import http.request.HttpRequest;
import http.request.HttpRequestParser;
import http.response.HttpResponse;
import http.response.HttpResponseParser;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.net.URISyntaxException;

public class RequestHandler implements Runnable {
    private static final Logger logger = LoggerFactory.getLogger(RequestHandler.class);

    private Socket connection;

    public RequestHandler(final Socket connectionSocket) {
        this.connection = connectionSocket;
    }

    public void run() {
        logger.debug("New Client Connect! Connected IP : {}, Port : {}/. ", connection.getInetAddress(),
                connection.getPort());

        try (InputStream inputStream = connection.getInputStream();
             OutputStream outputStream = connection.getOutputStream()) {

            DataOutputStream dataOutputStream = new DataOutputStream(outputStream);
            HttpRequest httpRequest = HttpRequestParser.parse(inputStream);
            HttpResponse httpResponse = new HttpResponse();
            Dispatcher.dispatch(httpRequest, httpResponse);

            logger.debug("{}", httpResponse);
            HttpResponseParser.send(dataOutputStream, httpResponse);

        } catch (IOException | URISyntaxException e) {
            logger.debug(e.getMessage());
        }
    }
}
