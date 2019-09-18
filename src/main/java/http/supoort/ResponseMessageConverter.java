package http.supoort;

import http.model.HttpResponse;
import http.model.HttpStatus;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.DataOutputStream;
import java.io.IOException;

public class ResponseMessageConverter {
    private static final Logger logger = LoggerFactory.getLogger(ResponseMessageConverter.class);

    public static void convert(HttpResponse httpResponse, DataOutputStream dos) {
        if (httpResponse.getHttpStatus() == HttpStatus.OK) {
            responseHeader(httpResponse, dos);
            responseBody(httpResponse.getBody(), dos);
        }
        if (httpResponse.getHttpStatus() == HttpStatus.FOUND) {
            responseRedirectHeader(httpResponse, dos);
        }
    }

    private static void responseRedirectHeader(HttpResponse httpResponse, DataOutputStream dos) {
        try {
            dos.writeBytes(httpResponse.getProtocol().getProtocol() + " " + httpResponse.getHttpStatus().getMessage() + "\r\n");
            dos.writeBytes("Location: http://localhost:8080/index.html\r\n");
            dos.writeBytes("Content-Type: " + httpResponse.getContentType() + "\r\n");
            dos.writeBytes("\r\n");
            dos.flush();
        } catch (IOException e) {
            logger.error(e.getMessage());
        }
    }


    private static void responseHeader(HttpResponse httpResponse, DataOutputStream dos) {
        try {
            dos.writeBytes(httpResponse.getProtocol().getProtocol() + " " + httpResponse.getHttpStatus().getMessage() + " \r\n");
            dos.writeBytes("Content-Type: " + httpResponse.getContentType().getType() + "\r\n");
            dos.writeBytes("Content-Length: " + httpResponse.getBody().length + "\r\n");
            dos.writeBytes("\r\n");
        } catch (IOException e) {
            logger.error(e.getMessage());
        }
    }

    private static void responseBody(byte[] body, DataOutputStream dos) {
        try {
            dos.write(body, 0, body.length);
            dos.flush();
        } catch (IOException e) {
            logger.error(e.getMessage());
        }
    }
}
