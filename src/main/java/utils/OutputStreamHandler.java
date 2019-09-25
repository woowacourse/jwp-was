package utils;

import model.http.HttpResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import webserver.RequestHandler;

import java.io.DataOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.net.URISyntaxException;

public class OutputStreamHandler {
    private static final Logger logger = LoggerFactory.getLogger(RequestHandler.class);

    public static void handle(OutputStream out, HttpResponse httpResponse) throws IOException, URISyntaxException {
        handleOutputStream(out, httpResponse);
    }

    private static void handleOutputStream(OutputStream out, HttpResponse httpResponse) throws IOException, URISyntaxException {
        DataOutputStream dos = new DataOutputStream(out);
        if (httpResponse.isNotInitialized() || httpResponse.hasError()) {
            byte[] body = FileIoUtils.loadFileFromClasspath("./templates/error.html");
            createResponse(httpResponse, dos, body);
            return;
        }

        byte[] body = FileIoUtils.loadFileFromClasspath(httpResponse.getPath());
        createResponse(httpResponse, dos, body);
    }

    private static void createResponse(HttpResponse httpResponse, DataOutputStream dos, byte[] body) {
        responseHeader(dos, body.length, httpResponse);
        responseBody(dos, body);
    }

    private static void responseHeader(DataOutputStream dos, int lengthOfBodyContent, HttpResponse response) {
        try {
            dos.writeBytes("HTTP/1.1 " + response.getHttpStatusCode() + " " + response.getHttpReasonPhrase() + " \r\n");
            if (response.isRedirect()) {
                dos.writeBytes("Location: " + response.getResourceName() + " \r\n");
            }
            dos.writeBytes("Content-Type: " + response.getMediaType() + ";charset=utf-8\r\n");
            dos.writeBytes("Content-Length: " + lengthOfBodyContent + "\r\n");
            dos.writeBytes("\r\n");
        } catch (IOException e) {
            logger.error(e.getMessage());
        }
    }

    private static void responseBody(DataOutputStream dos, byte[] body) {
        try {
            dos.write(body, 0, body.length);
            dos.flush();
        } catch (IOException e) {
            logger.error(e.getMessage());
        }
    }
}
