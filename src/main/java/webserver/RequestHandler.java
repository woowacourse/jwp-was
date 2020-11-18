package webserver;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.Socket;
import java.net.URISyntaxException;
import java.nio.charset.StandardCharsets;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import controller.DispatcherServlet;
import controller.HttpServlet;
import http.ContentType;
import http.HttpRequest;
import http.HttpResponse;
import http.SimpleHttpRequest;
import utils.FileIoUtils;
import utils.StaticResourceMatcher;

public class RequestHandler implements Runnable {
    private static final Logger logger = LoggerFactory.getLogger(RequestHandler.class);

    private Socket connection;

    public RequestHandler(Socket connectionSocket) {
        this.connection = connectionSocket;
    }

    public void run() {
        logger.debug("New Client Connect! Connected IP : {}, Port : {}", connection.getInetAddress(),
            connection.getPort());

        try (
            InputStream inputStream = connection.getInputStream();
            OutputStream outputStream = connection.getOutputStream();
            InputStreamReader inputStreamReader = new InputStreamReader(inputStream, StandardCharsets.UTF_8);
            BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
            DataOutputStream dos = new DataOutputStream(outputStream);
        ) {
            HttpServlet dispatcherServlet = new DispatcherServlet();
            HttpRequest httpRequest = SimpleHttpRequest.of(bufferedReader);
            logger.debug(System.lineSeparator() + httpRequest.toString());
            HttpResponse httpResponse = new HttpResponse();
            if (StaticResourceMatcher.isStaticResourcePath(httpRequest.getURI())) {
                byte[] body = FileIoUtils.loadFileFromClasspath(httpRequest.getURI());
                ContentType contentType = ContentType.findByURI(httpRequest.getURI());
                httpResponse.setBody(body, contentType);
            } else {
                dispatcherServlet.service(httpRequest, httpResponse);
            }
            httpResponse.send(dos);
        } catch (IOException | URISyntaxException e) {
            logger.error(e.getMessage());
        }
    }
}
