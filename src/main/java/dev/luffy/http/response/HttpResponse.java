package dev.luffy.http.response;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.DataOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.net.URISyntaxException;

import dev.luffy.http.MimeType;
import dev.luffy.http.request.HttpRequest;
import dev.luffy.utils.FileIoUtils;
import dev.luffy.utils.HttpRequestUtils;

public class HttpResponse {

    private static final Logger logger = LoggerFactory.getLogger(HttpResponse.class);

    private DataOutputStream dos;

    public HttpResponse(OutputStream out) {
        dos = new DataOutputStream(out);
    }

    public void staticFileResource(HttpRequest httpRequest) {
        send200(httpRequest);
    }

    public void send200(HttpRequest httpRequest) {
        MimeType mimeType = MimeType.of(httpRequest.pathExtension());
        String filePath = HttpRequestUtils.filePathBuilder(httpRequest.getPath(), mimeType);

        try {
            byte[] body = FileIoUtils.loadFileFromClasspath(filePath);
            response200Header(dos, body.length, mimeType);
            responseBody(dos, body);
        } catch (IOException | URISyntaxException e) {
            e.printStackTrace();
        }
    }

    public void send302(String location) {
        response302Header(dos, location);
    }

    private void response302Header(DataOutputStream dos, String location) {
        try {
            dos.writeBytes("HTTP/1.1 302 Found \r\n");
            dos.writeBytes("Location: " + location + "\r\n");
            dos.writeBytes("\r\n");
        } catch (IOException e) {
            logger.error(e.getMessage());
        }
    }

    private void response200Header(DataOutputStream dos, int lengthOfBodyContent, MimeType mimeType) {
        try {
            dos.writeBytes("HTTP/1.1 200 OK \r\n");
            dos.writeBytes("Content-Type: " + mimeType.getMimeType() + ";charset=utf-8\r\n");
            dos.writeBytes("Content-Length: " + lengthOfBodyContent + "\r\n");
            dos.writeBytes("\r\n");
        } catch (IOException e) {
            logger.error(e.getMessage());
        }
    }

    private void responseBody(DataOutputStream dos, byte[] body) {
        try {
            dos.write(body, 0, body.length);
            dos.flush();
        } catch (IOException e) {
            logger.error(e.getMessage());
        }
    }

    public void send404() {
        byte[] body = new byte[0];
        try {
            body = FileIoUtils.loadFileFromClasspath("./templates/error/404.html");
        } catch (IOException e) {
            e.printStackTrace();
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }
        response404Header(dos, body.length, MimeType.HTML);
        responseBody(dos, body);
    }

    private void response404Header(DataOutputStream dos, int lengthOfBodyContent, MimeType mimeType) {
        try {
            dos.writeBytes("HTTP/1.1 404 NOT FOUND \r\n");
            dos.writeBytes("Content-Type: " + mimeType.getMimeType() + ";charset=utf-8\r\n");
            dos.writeBytes("Content-Length: " + lengthOfBodyContent + "\r\n");
            dos.writeBytes("\r\n");
        } catch (IOException e) {
            logger.error(e.getMessage());
        }
    }
}
