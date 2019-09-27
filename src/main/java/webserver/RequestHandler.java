package webserver;

import http.request.HttpRequest;
import http.request.HttpRequestFactory;
import http.request.HttpUri;
import http.response.Http200ResponseEntity;
import http.response.HttpResponse;
import http.response.HttpResponseEntity;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import utils.LoggingUtils;

import java.io.DataOutputStream;
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

        try (InputStream in = connection.getInputStream(); OutputStream out = connection.getOutputStream()) {
            HttpRequest request = HttpRequestFactory.getHttpRequest(in);
            logger.debug(request.toString());

            HttpResponse response = getResponseOf(request);

            writeResponse(out, response);
        } catch (IOException | URISyntaxException e) {
            LoggingUtils.logStackTrace(logger, e);
            logger.error(e.getMessage());
        }
    }

    private HttpResponse getResponseOf(HttpRequest request) throws IOException, URISyntaxException {
        HttpResponseEntity responseEntity = isStaticContentRequest(request)
                ? new Http200ResponseEntity(request.getUri().getPath())
                : ControllerMapper.map(request);
        return responseEntity.makeResponse();
    }

    private boolean isStaticContentRequest(HttpRequest request) {
        HttpUri uri = request.getUri();
        return uri.hasExtension();
    }

    private void writeResponse(OutputStream out, HttpResponse response) throws IOException {
        DataOutputStream dos = new DataOutputStream(out);
        dos.writeBytes(response.getHeaderMessage());
        if (response.hasBody()) {
            dos.write(response.getBody());
        }
    }
}
