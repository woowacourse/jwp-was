package webserver.support;

import http.cookie.Cookie;
import http.response.Response;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.DataOutputStream;
import java.io.IOException;

public class ResponseWriter {
    private static final Logger logger = LoggerFactory.getLogger(ResponseWriter.class);

    public static void write(DataOutputStream dos, Response response) {
        if (response.isOk()) {
            writeHeader(dos, response200Header(response));
            writeBody(dos, response);
            return;
        }
        writeHeader(dos, response302Header(response));
    }

    private static String response200Header(Response response) {
        return response.getHttpVersion() + " " + response.getStatusCode() + " " + response.getReasonPhrase() + "\r\n" +
                "Content-Type: " + response.getContentType() + "\r\n" +
                "Content-Length: " + response.getContentLength() + "\r\n" +
                "\r\n";
    }

    private static String response302Header(Response response) {
        return response.getHttpVersion() + " " + response.getStatusCode() + " " + response.getReasonPhrase() + "\r\n" +
                "Location: " + response.getLocation() + "\r\n" +
                writeCookies(response) +
                "\r\n";
    }

    private static String writeCookies(Response response) {
        StringBuilder sb = new StringBuilder();
        for (Cookie cookie : response.getCookies()) {
            sb.append(cookie.toString());
        }
        return sb.toString();
    }

    private static void writeHeader(DataOutputStream dos, String message) {
        try {
            dos.writeBytes(message);
        } catch (IOException e) {
            logger.error(e.getMessage());
        }
    }

    private static void writeBody(DataOutputStream dos, Response response) {
        try {
            dos.write(response.getContentBody(), 0, response.getContentLength());
            dos.flush();
        } catch (IOException e) {
            logger.error(e.getMessage());
        }
    }
}
