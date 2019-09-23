package webserver;

import webserver.httpRequest.HttpStatus;

import java.io.DataOutputStream;
import java.io.IOException;

public class ResponseProcessor {

    private ResponseProcessor() {
    }

    public static ResponseProcessor getInstance() {
        return LazyHolder.INSTANCE;
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

    private void response200Header(DataOutputStream dos, int contentLength, HttpResponse httpResponse) throws IOException {
        dos.writeBytes("HTTP/1.1 " + httpResponse.getStatusCodeAndMessage() + " \r\n");
        dos.writeBytes("Content-Type: " + httpResponse.getContentType() + "\r\n");
        dos.writeBytes("Content-Length: " + contentLength + "\r\n");
        dos.writeBytes("\r\n");
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
            e.printStackTrace();
        }
    }

    private void response300Header(DataOutputStream dos, String location, HttpResponse httpResponse) throws IOException {
        dos.writeBytes("HTTP/1.1 " + httpResponse.getStatusCodeAndMessage() + " \r\n");
        dos.writeBytes("Location: " + location + "\r\n");
        dos.writeBytes("\r\n");
        dos.flush();
    }

    public void sendError(DataOutputStream dos, String errorCode, HttpResponse httpResponse) {
        httpResponse.setHttpStatus(HttpStatus.values(errorCode));
        try {
            response400Header(dos, httpResponse);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void response400Header(DataOutputStream dos, HttpResponse httpResponse) throws IOException {
        dos.writeBytes("HTTP/1.1 " + httpResponse.getStatusCodeAndMessage() + " \r\n");
        dos.writeBytes("\r\n");
        dos.flush();
    }

    private static class LazyHolder {
        private static final ResponseProcessor INSTANCE = new ResponseProcessor();
    }

}
