package http.supoort;

import http.model.HttpResponse;
import http.model.HttpStatus;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.DataOutputStream;
import java.io.IOException;

public class ResponseMessageConverter {
    private static final Logger logger = LoggerFactory.getLogger(ResponseMessageConverter.class);
    private static final String SEPARATOR = " ";
    private static final String LINE_BREAK = "\r\n";
    private static final String LOCATION_TO_ROOT_URI = "Location: http://localhost:8080/index.html";

    public static void convert(HttpResponse httpResponse, DataOutputStream dos) {
        if (httpResponse.getStatusLine().getHttpStatus() == HttpStatus.OK) {
            responseHeader(httpResponse, dos);
            responseBody(httpResponse.getBody(), dos);
        }
        if (httpResponse.getStatusLine().getHttpStatus() == HttpStatus.FOUND) {
            responseRedirectHeader(httpResponse, dos);
        }
    }

    private static void responseRedirectHeader(HttpResponse httpResponse, DataOutputStream dos) {
        try {
            dos.writeBytes(httpResponse.getStatusLine().getHttpProtocols() + SEPARATOR + httpResponse.getStatusLine().getHttpStatus().getMessage() + LINE_BREAK);
            dos.writeBytes(LOCATION_TO_ROOT_URI + LINE_BREAK);
            dos.writeBytes("Content-Type: " + httpResponse.getContentType() + LINE_BREAK);
            dos.writeBytes(LINE_BREAK);
            dos.flush();
        } catch (IOException e) {
            logger.error(e.getMessage());
            throw new IllegalHttpRequestException();
        }
    }

    private static void responseHeader(HttpResponse httpResponse, DataOutputStream dos) {
        try {
            dos.writeBytes(httpResponse.getStatusLine().getHttpProtocols() + " " + httpResponse.getStatusLine().getHttpStatus().getMessage() + LINE_BREAK);
            dos.writeBytes("Content-Type: " + httpResponse.getContentType().getType() + LINE_BREAK);
            dos.writeBytes("Content-Length: " + httpResponse.getBody().length + LINE_BREAK);
            dos.writeBytes(LINE_BREAK);
        } catch (IOException e) {
            logger.error(e.getMessage());
            throw new IllegalHttpRequestException();
        }
    }

    private static void responseBody(byte[] body, DataOutputStream dos) {
        try {
            dos.write(body, 0, body.length);
            dos.flush();
        } catch (IOException e) {
            logger.error(e.getMessage());
            throw new IllegalHttpRequestException();
        }
    }
}
