package webserver;

import webserver.http.HttpResponse;
import webserver.http.cookie.Cookie;
import webserver.http.httpRequest.HttpStatus;

import java.io.DataOutputStream;
import java.io.IOException;
import java.util.Map;

public class ResponseProcessor {

    private static final String HTTP_VERSION = "HTTP/1.1 ";
    private static final String HEADER_LINE_SEPARATOR = "\r\n";
    private static final String HEADER_SEPARATOR = ": ";

    private ResponseProcessor() {
    }

    public static ResponseProcessor getInstance() {
        return LazyHolder.INSTANCE;
    }

    public void forward(DataOutputStream dos, byte[] bytes, HttpResponse httpResponse) {
        try {
            httpResponse.setHttpStatus(HttpStatus.OK);
            writeResponseHeader(dos, httpResponse);
            writeResponseBody(dos, bytes);
        } catch (IOException e) {
            throw new IllegalArgumentException();
        }
    }

    private void writeResponseHeader(DataOutputStream dos, HttpResponse httpResponse) throws IOException {
        dos.writeBytes(HTTP_VERSION + httpResponse.getStatusCodeAndMessage() + HEADER_LINE_SEPARATOR);
        writeResponseHeaders(dos, httpResponse);
        writeCookies(dos, httpResponse);
        dos.writeBytes(HEADER_LINE_SEPARATOR);
    }

    private void writeResponseHeaders(DataOutputStream dos, HttpResponse httpResponse) throws IOException {
        for (Map.Entry<String, String> stringStringEntry : httpResponse.getHeaders().entrySet()) {
            String key = stringStringEntry.getKey();
            String value = stringStringEntry.getValue();
            dos.writeBytes(key + HEADER_SEPARATOR + value + HEADER_LINE_SEPARATOR);
        }
    }

    private void writeCookies(DataOutputStream dos, HttpResponse httpResponse) throws IOException {
        Map<String, Cookie> cookies = httpResponse.getCookies();
        for (Map.Entry<String, Cookie> stringCookieEntry : cookies.entrySet()) {
            dos.writeBytes(stringCookieEntry.getValue().toString() + HEADER_LINE_SEPARATOR);
        }
    }

    private void writeResponseBody(DataOutputStream dos, byte[] body) throws IOException {
        dos.write(body, 0, body.length);
        dos.flush();
    }

    public void sendRedirect(DataOutputStream dos, HttpResponse httpResponse) {
        httpResponse.setHttpStatus(HttpStatus.FOUND);
        try {
            writeResponseHeader(dos, httpResponse);
        } catch (IOException e) {
            throw new IllegalArgumentException();
        }
    }

    public void sendError(DataOutputStream dos, HttpStatus httpStatus, HttpResponse httpResponse) {
        httpResponse.setHttpStatus(httpStatus);
        try {
            writeResponseHeader(dos, httpResponse);
        } catch (IOException e) {
            throw new IllegalArgumentException();
        }
    }

    private static class LazyHolder {
        private static final ResponseProcessor INSTANCE = new ResponseProcessor();
    }
}
