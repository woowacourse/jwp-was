package webserver;

import webserver.httpRequest.HttpStatus;

import java.io.DataOutputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class HttpResponse {
    private Map<String, String> header = new HashMap<>();
    private HttpStatus httpStatus;

    private void setHttpStatus(HttpStatus httpStatus) {
        this.httpStatus = httpStatus;
    }

    private String getContentType() {
        String contentType = header.get("Content-Type");
        if (contentType == null) {
            return "text/plain";
        }
        return contentType;
    }

    public void setContentType(String contentType) {
        header.put("Content-Type", contentType);
    }

    public String getStatusCodeAndMessage() {
        return httpStatus.getCodeAndMessage();
    }

    public void forward(DataOutputStream dos, byte[] bytes) {
        try {
            setHttpStatus(HttpStatus.OK);
            response200Header(dos, bytes.length);
            responseBody(dos, bytes);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void response200Header(DataOutputStream dos, int contentLength) throws IOException {
        dos.writeBytes("HTTP/1.1 " + getStatusCodeAndMessage() + " \r\n");
        dos.writeBytes("Content-Type: " + getContentType() + "\r\n");
        dos.writeBytes("Content-Length: " + contentLength + "\r\n");
        dos.writeBytes("\r\n");
    }

    private void responseBody(DataOutputStream dos, byte[] body) throws IOException {
        dos.write(body, 0, body.length);
        dos.flush();
    }

    public void sendRedirect(DataOutputStream dos, String location) {
        setHttpStatus(HttpStatus.FOUND);
        try {
            response300Header(dos, location);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void response300Header(DataOutputStream dos, String location) throws IOException {
        dos.writeBytes("HTTP/1.1 " + getStatusCodeAndMessage() + " \r\n");
        dos.writeBytes("Location: " + location + "\r\n");
        dos.writeBytes("\r\n");
        dos.flush();
    }

    public void error(DataOutputStream dos, String errorCode) {
        setHttpStatus(HttpStatus.NOT_FOUND);
        try {
            response400Header(dos, errorCode);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void response400Header(DataOutputStream dos, String errorCode) throws IOException {
        dos.writeBytes("HTTP/1.1 " + getStatusCodeAndMessage() + " \r\n");
        dos.writeBytes("Location: " + errorCode + "\r\n");
        dos.writeBytes("\r\n");
        dos.flush();
    }
}
