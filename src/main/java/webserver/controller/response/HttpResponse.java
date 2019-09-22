package webserver.controller.response;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import webserver.controller.request.header.ContentType;

import java.io.DataOutputStream;
import java.io.IOException;
import java.io.OutputStream;

public class HttpResponse {
    private static final Logger logger = LoggerFactory.getLogger(HttpResponse.class);
    private final DataOutputStream dataOutputStream;

    public HttpResponse(OutputStream outputStream) {
        this.dataOutputStream = new DataOutputStream(outputStream);
    }

    public void response200Header(int lengthOfBodyContent, ContentType contentType) {
        try {
            dataOutputStream.writeBytes("HTTP/1.1 200 OK \r\n");
            dataOutputStream.writeBytes("Content-Type: text/"  + contentType + ";charset=utf-8\r\n");
            dataOutputStream.writeBytes("Content-Length: " + lengthOfBodyContent + "\r\n");
            dataOutputStream.writeBytes("\r\n");
        } catch (IOException e) {
            logger.error(e.getMessage());
        }
    }

    public void responseBody(byte[] body) {
        try {
            dataOutputStream.write(body, 0, body.length);
            dataOutputStream.flush();
        } catch (IOException e) {
            logger.error(e.getMessage());
        }
    }

    public void redirect302Found(String redirectUrl) {
        logger.debug("redirect302Found >>>");
        try {
            dataOutputStream.writeBytes("HTTP/1.1 302 FOUND \r\n");
            dataOutputStream.writeBytes("Location: " + redirectUrl + "\r\n");
            dataOutputStream.writeBytes("Connection: close \r\n");
            dataOutputStream.writeBytes("\r\n");
            dataOutputStream.flush();
        } catch (IOException e) {
            logger.error(e.getMessage());
        }
    }

    public void badRequest(String errorMessage) {
        try {
            dataOutputStream.writeBytes("HTTP/1.1 400 BAD REQUEST"+ errorMessage + "\r\n");
            dataOutputStream.writeBytes("Server: jwp-was(robby) \r\n");
            dataOutputStream.writeBytes("Connection: close \r\n");
            dataOutputStream.flush();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }
}
