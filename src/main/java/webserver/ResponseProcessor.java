package webserver;

import webserver.httpRequest.HttpStatus;

import java.io.DataOutputStream;
import java.io.IOException;

public class ResponseProcessor {

    private static final String HTTP_VERSION = "HTTP/1.1 ";
    private static final String HEADER_LINE_SEPARATOR = "\r\n";

    private ResponseProcessor() {
    }

    public static ResponseProcessor getInstance() {
        return LazyHolder.INSTANCE;
    }

    private void response200Header(DataOutputStream dos, int contentLength, HttpResponse httpResponse) throws IOException {
        dos.writeBytes(HTTP_VERSION + httpResponse.getStatusCodeAndMessage() + HEADER_LINE_SEPARATOR);
        dos.writeBytes("Content-Type: " + httpResponse.getContentType() + HEADER_LINE_SEPARATOR);
        dos.writeBytes("Content-Length: " + contentLength + HEADER_LINE_SEPARATOR);
        dos.writeBytes(HEADER_LINE_SEPARATOR);
    }

    public void forward(DataOutputStream dos, byte[] bytes, HttpResponse httpResponse) {
        try {
            httpResponse.setHttpStatus(HttpStatus.OK);
            response200Header(dos, bytes.length, httpResponse);
            responseBody(dos, bytes);
        } catch (IOException e) {
            throw new IllegalArgumentException();
        }
    }

    private void response300Header(DataOutputStream dos, String location, HttpResponse httpResponse) throws IOException {
        dos.writeBytes(HTTP_VERSION + httpResponse.getStatusCodeAndMessage() + HEADER_LINE_SEPARATOR);
        dos.writeBytes("Location: " + location + HEADER_LINE_SEPARATOR);
        dos.writeBytes(HEADER_LINE_SEPARATOR);
        dos.flush();
    }

    private void responseBody(DataOutputStream dos, byte[] body) throws IOException {
        dos.write(body, 0, body.length);
        dos.flush();
    }

    public void sendRedirect(DataOutputStream dos, String location, HttpResponse httpResponse) {
        httpResponse.setHttpStatus(HttpStatus.FOUND);
        try {
            response300Header(dos, location, httpResponse);
        } catch (IOException e) {
            throw new IllegalArgumentException();
        }
    }

    private void response400Header(DataOutputStream dos, HttpResponse httpResponse) throws IOException {
        dos.writeBytes(HTTP_VERSION + httpResponse.getStatusCodeAndMessage() + HEADER_LINE_SEPARATOR);
        dos.writeBytes(HEADER_LINE_SEPARATOR);
        dos.flush();
    }

    public void sendError(DataOutputStream dos, String errorCode, HttpResponse httpResponse) {
        httpResponse.setHttpStatus(HttpStatus.values(errorCode));
        try {
            response400Header(dos, httpResponse);
        } catch (IOException e) {
            throw new IllegalArgumentException();
        }
    }

    private static class LazyHolder {
        private static final ResponseProcessor INSTANCE = new ResponseProcessor();
    }
}
