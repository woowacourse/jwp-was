package webserver;

import java.io.DataOutputStream;
import java.io.IOException;
import java.util.Map;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import webserver.http.response.HttpResponse;
import webserver.http.response.HttpResponseHeaders;
import webserver.http.response.HttpStatus;
import webserver.http.response.HttpStatusLine;

public class ResponseSender {
    private static final Logger logger = LoggerFactory.getLogger(ResponseSender.class);

    private ResponseSender() {
    }

    public static void send(DataOutputStream dos, HttpResponse response) {
        try {
            sendResponseHeader(dos, response);
            sendResponseBodyIfPresent(dos, response);
        } catch (IOException e) {
            logger.error(e.getMessage());
        }
    }

    private static void sendResponseHeader(DataOutputStream dos, HttpResponse response) throws IOException {
        String responseLine = parseResponseLine(response.getStatusLine());
        String responseHeaders = parseResponseHeaders(response.getResponseHeaders());
        dos.writeBytes(responseLine + responseHeaders);
    }

    private static String parseResponseLine(HttpStatusLine httpStatusLine) {
        final HttpStatus httpStatus = httpStatusLine.getHttpStatus();
        return String.format("%s %d %s\r\n", httpStatusLine.getVersion(), httpStatus.getStatusCode(),
                httpStatus.getReasonPhrase());
    }

    private static String parseResponseHeaders(HttpResponseHeaders responseHeaders) {
        StringBuilder stringBuilder = new StringBuilder();
        final Map<String, String> headers = responseHeaders.getResponseHeaders();
        final Set<String> keys = headers.keySet();
        for (String key : keys) {
            stringBuilder.append(key);
            stringBuilder.append(":");
            stringBuilder.append(" ");
            stringBuilder.append(headers.get(key));
            stringBuilder.append("\r\n");
        }
        stringBuilder.append("\r\n");
        return stringBuilder.toString();
    }

    private static void sendResponseBodyIfPresent(DataOutputStream dos, HttpResponse response) throws IOException {
        final byte[] body = response.getBody();
        if (body == null) {
            return;
        }
        dos.write(body, 0, body.length);
    }
}
