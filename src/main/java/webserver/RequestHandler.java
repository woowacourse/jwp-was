package webserver;

import http.request.HttpRequest;
import http.request.HttpRequestFactory;
import http.response.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import utils.FileIoUtils;

import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.net.URISyntaxException;

public class RequestHandler implements Runnable {
    private static final Logger logger = LoggerFactory.getLogger(RequestHandler.class);
    private static final String DEFAULT_PATH = "./templates";
    private static final String STATIC_PATH = "./static";

    private Socket connection;

    public RequestHandler(Socket connectionSocket) {
        this.connection = connectionSocket;
    }

    public void run() {
        logger.debug("New Client Connect! Connected IP : {}, Port : {}", connection.getInetAddress(),
                connection.getPort());

        try (InputStream in = connection.getInputStream(); OutputStream out = connection.getOutputStream()) {
            HttpRequest request = HttpRequestFactory.makeHttpRequest(in);
            logger.debug(request.toString());

            DataOutputStream dos = new DataOutputStream(out);

            if (request.getUri().hasExtension()) {
                send200Response(request, dos, request.getUri().getPath());
            } else {
                HttpResponseEntity responseEntity = ControllerMapper.map(request);

                if (responseEntity.getStatus().match(HttpStatus.FOUND)) {
                    send302Response(dos);
                } else if (responseEntity.getStatus().match(HttpStatus.OK)) {
                    send200Response(request, dos, responseEntity.getViewTemplatePath());
                } else if (responseEntity.getStatus().match(HttpStatus.NOT_FOUND)) {
                    send404Response(request, dos, responseEntity);
                }
            }
        } catch (IOException | URISyntaxException e) {
            logger.error(e.getMessage());
        }
    }

    private void send302Response(DataOutputStream dos) throws IOException {
        HttpResponse response = HttpResponseFactory.makeHttp302Response("http://localhost:8080/index.html");

        dos.writeBytes(response.getHeaderMessage());
    }

    private void send200Response(HttpRequest request, DataOutputStream dos, String viewTemplatePath) throws IOException, URISyntaxException {
        byte[] body = getBody(viewTemplatePath);
        HttpResponseBody responseBody = new HttpResponseBody(body, request.getResponseContentsType());
        HttpResponse response = HttpResponseFactory.makeHttp200Response(responseBody);

        dos.writeBytes(response.getHeaderMessage());
        dos.write(response.getBody());
    }

    private void send404Response(HttpRequest request, DataOutputStream dos, HttpResponseEntity responseEntity) throws IOException, URISyntaxException {
        byte[] body = getBody(responseEntity.getViewTemplatePath());
        HttpResponseBody responseBody = new HttpResponseBody(body, request.getResponseContentsType());
        HttpResponse response = HttpResponseFactory.makeHttp404Response(responseBody);

        dos.writeBytes(response.getHeaderMessage());
        dos.write(response.getBody());
    }

    private byte[] getBody(String path) throws IOException, URISyntaxException {
        byte[] body;
        try {
            body = FileIoUtils.loadFileFromClasspath(DEFAULT_PATH + path);
        } catch (NullPointerException e) {
            body = FileIoUtils.loadFileFromClasspath(STATIC_PATH + path);
        }
        return body;
    }
}
