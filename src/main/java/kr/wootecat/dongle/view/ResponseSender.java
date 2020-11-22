package kr.wootecat.dongle.view;

import static com.google.common.net.HttpHeaders.*;
import static java.lang.String.*;
import static kr.wootecat.dongle.model.http.Cookie.*;

import java.io.DataOutputStream;
import java.io.IOException;
import java.util.Collection;
import java.util.Map;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import kr.wootecat.dongle.model.http.Cookie;
import kr.wootecat.dongle.model.http.HttpStatus;
import kr.wootecat.dongle.model.http.response.HttpResponse;
import kr.wootecat.dongle.model.http.response.HttpResponseHeaders;
import kr.wootecat.dongle.model.http.response.HttpStatusLine;

public class ResponseSender {

    private static final String COOKIE_PAIR_APPEND_DELIMITER = "; ";
    private static final String KEY_VALUE_APPEND_DELIMITER = "=";

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
        HttpStatus httpStatus = httpStatusLine.getHttpStatus();
        return format("%s %d %s\r\n", httpStatusLine.getVersion(), httpStatus.getCode(),
                httpStatus.getReasonPhrase());
    }

    private static String parseResponseHeaders(HttpResponseHeaders responseHeaders) {
        StringBuilder stringBuilder = new StringBuilder();
        Map<String, String> headers = responseHeaders.getResponseHeaders();
        Set<String> keys = headers.keySet();
        for (String key : keys) {
            stringBuilder.append(key);
            stringBuilder.append(":");
            stringBuilder.append(" ");
            stringBuilder.append(headers.get(key));
            stringBuilder.append("\r\n");
        }
        Collection<Cookie> cookies = responseHeaders.getCookies();
        for (Cookie cookie : cookies) {
            String name = cookie.getName();
            String value = cookie.getValue();

            String result = format("%s%s%s", name, KEY_VALUE_APPEND_DELIMITER, value);
            if (cookie.hasPath()) {
                result += COOKIE_PAIR_APPEND_DELIMITER;
                result += format("%s%s%s", PATH_ATTRIBUTE_NAME, KEY_VALUE_APPEND_DELIMITER, cookie.getPath());
            }
            stringBuilder.append(SET_COOKIE);
            stringBuilder.append(":");
            stringBuilder.append(" ");
            stringBuilder.append(result);
            stringBuilder.append("\r\n");
        }

        stringBuilder.append("\r\n");
        return stringBuilder.toString();
    }

    private static void sendResponseBodyIfPresent(DataOutputStream dos, HttpResponse response) throws IOException {
        byte[] body = response.getBody();
        if (body == null) {
            return;
        }
        dos.write(body, 0, body.length);
    }
}
