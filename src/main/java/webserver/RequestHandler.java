package webserver;

import controller.Controller;
import controller.ControllerMapper;
import http.HttpRequest;
import http.factory.HttpRequestFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import utils.FileIoUtils;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
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
            BufferedReader br = new BufferedReader(new InputStreamReader(in, StandardCharsets.UTF_8));
            DataOutputStream dos = new DataOutputStream(out);

            HttpRequest httpRequest = HttpRequestFactory.createRequest(br);

            ControllerMapper.from(httpRequest.getRequestLine()).ifPresent(
                    mapper -> Controller.findMethod(mapper).accept(httpRequest.getParams(), dos)
            );

            if (dos.size() == 0) {
                byte[] body = FileIoUtils.loadFileFromClasspath(findFilePath(httpRequest));
                responseHeader(dos, httpRequest, body);
                responseBody(dos, body);
            }
        } catch (IOException e) {
            logger.error(e.getMessage());
        }
    }

    private String findFilePath(HttpRequest httpRequest) {
        String url = httpRequest.getUrl();
        String baseUrl = "./static";
        if (url.contains(".html") || url.contains("favicon.ico")) {
            baseUrl = "./templates";
        }
        return baseUrl + url;
    }

    private void responseHeader(DataOutputStream dos, HttpRequest httpRequest, byte[] body) throws IOException {
        dos.writeBytes("HTTP/1.1 200 OK \r\n");
        dos.writeBytes("Content-Type: " + findContentType(httpRequest) + ";charset=utf-8\r\n");
        dos.writeBytes("Content-Length: " + body.length + "\r\n");
        dos.writeBytes("\r\n");
    }

    private String findContentType(HttpRequest httpRequest) {
        String contentType = "text/html";
        if (httpRequest.getUrl().contains(".css")) {
            contentType = "text/css";
        }
        return contentType;
    }

    private void responseBody(DataOutputStream dos, byte[] body) {
        try {
            dos.write(body, 0, body.length);
            dos.flush();
        } catch (IOException e) {
            logger.error(e.getMessage());
        }
    }
}
