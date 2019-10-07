package http.response;

import http.common.HttpCookie;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.DataOutputStream;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Map;

public class HttpResponseSender {
    private static final Logger logger = LoggerFactory.getLogger(HttpResponseSender.class);
    private static final String SET_COOKIE_PREFIX = "Set-Cookie: ";
    private static final String COOKIE_VALUE_DELIMITER = "=";
    private static final String EXPIRES = "Expires=";
    private static final String MAX_AGE = "Max-Age=";
    private static final String DOMAIN = "Domain=";
    private static final String PATH = "Path=";
    private static final String SECURE = "Secure";
    private static final String HTTP_ONLY = "HttpOnly";
    private static final String COOKIE_PROPERTY_DELIMITER = "; ";

    public static void send(final DataOutputStream dos, final HttpResponse httpResponse) {
        try {
            responseHeader(dos, httpResponse);
            responseBody(dos, httpResponse.getHttpResponseBody());
            dos.flush();
        } catch (IOException e) {
            logger.error(e.getMessage());
        }
    }

    private static void responseHeader(final DataOutputStream dos, final HttpResponse httpResponse) throws IOException {
        dos.writeBytes(httpResponse.getStatusLine().getHttpVersion() + " " + httpResponse.getStatusLine().getHttpStatus() + "\r\n");

        for (Map.Entry<String, String> element : httpResponse.getHeaders()) {
            dos.writeBytes(element.getKey() + ": " + element.getValue() + "\r\n");
        }

        writeSetCookieHeader(dos, httpResponse.getHttpCookies());
        dos.writeBytes("\r\n");
    }

    private static void writeSetCookieHeader(final DataOutputStream dos, final List<HttpCookie> httpCookies) {
        httpCookies.forEach(httpCookie -> {
            try {
                dos.writeBytes(convertCookieToString(httpCookie) + "\r\n");
            } catch (IOException e) {
                logger.error("cookie write error!: {}", e.getMessage());
            }
        });
    }

    private static String convertCookieToString(final HttpCookie httpCookie) {
        StringBuilder stringBuilder = new StringBuilder(SET_COOKIE_PREFIX + httpCookie.getName() + COOKIE_VALUE_DELIMITER + httpCookie.getValue());

        LocalDateTime expires = httpCookie.getExpires();
        if (expires != null) {
            appendCookiePropertyDelimiter(stringBuilder)
                    .append(EXPIRES)
                    .append(expires.format(DateTimeFormatter.RFC_1123_DATE_TIME));
        }

        int maxAge = httpCookie.getMaxAge();
        if (maxAge != -1) {
            appendCookiePropertyDelimiter(stringBuilder)
                    .append(MAX_AGE)
                    .append(maxAge);
        }

        String domain = httpCookie.getDomain();
        if (domain != null) {
            appendCookiePropertyDelimiter(stringBuilder)
                    .append(DOMAIN)
                    .append(domain);
        }

        String path = httpCookie.getPath();
        if (path != null) {
            appendCookiePropertyDelimiter(stringBuilder)
                    .append(PATH)
                    .append(path);
        }

        if (httpCookie.isSecure()) {
            appendCookiePropertyDelimiter(stringBuilder)
                    .append(SECURE);
        }

        if (httpCookie.isHttpOnly()) {
            appendCookiePropertyDelimiter(stringBuilder)
                    .append(HTTP_ONLY);
        }

        return stringBuilder.toString();
    }

    private static StringBuilder appendCookiePropertyDelimiter(StringBuilder stringBuilder) {
        return stringBuilder.append(COOKIE_PROPERTY_DELIMITER);
    }

    private static void responseBody(DataOutputStream dos, HttpResponseBody httpResponseBody) {
        try {
            dos.write(httpResponseBody.getBody(), 0, httpResponseBody.getLength());
            dos.flush();
        } catch (IOException e) {
            logger.error(e.getMessage());
        }
    }
}
