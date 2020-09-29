package http.response;

import http.request.HttpRequest;
import utils.FileIoUtils;

import java.io.DataOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.net.URISyntaxException;
import java.util.Map;

import static com.google.common.net.HttpHeaders.ACCEPT;

public class HttpResponse {
    private final DataOutputStream dos;

    public HttpResponse(OutputStream outputStream) {
        this.dos = new DataOutputStream(outputStream);
    }

    public void forward(HttpRequest httpRequest) throws IOException, URISyntaxException {
        byte[] body = FileIoUtils.loadFileFromClasspath(httpRequest.getPath());
        responseHeader(httpRequest, body);
        responseBody(body);
    }

    public void forwardDynamicPage(HttpRequest httpRequest, byte[] body) throws IOException {
        dos.writeBytes("HTTP/1.1 200 OK " + System.lineSeparator());
        dos.writeBytes("Content-Type: " + findContentType(httpRequest) + System.lineSeparator());
        dos.writeBytes("Content-Length: " + body.length + System.lineSeparator());
        dos.writeBytes(System.lineSeparator());
        dos.write(body);
    }

    private void responseHeader(HttpRequest httpRequest, byte[] body) throws IOException {
        dos.writeBytes("HTTP/1.1 200 OK " + System.lineSeparator());
        dos.writeBytes("Content-Type: " + findContentType(httpRequest) + System.lineSeparator());
        dos.writeBytes("Content-Length: " + body.length + System.lineSeparator());
        dos.writeBytes(System.lineSeparator());
    }

    private String findContentType(HttpRequest httpRequest) {
        return httpRequest.getHeader(ACCEPT).split(",")[0];
    }

    private void responseBody(byte[] body) throws IOException {
        dos.write(body, 0, body.length);
        dos.flush();
    }

    public void sendRedirect(String redirectPath) {
        try {
            dos.writeBytes("HTTP/1.1 302 FOUND " + System.lineSeparator());
            dos.writeBytes("Location: " + redirectPath + System.lineSeparator());
            dos.writeBytes(System.lineSeparator());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void sendRedirect(String redirectPath, Map<String, String> headers) {
        try {
            dos.writeBytes("HTTP/1.1 302 FOUND " + System.lineSeparator());
            dos.writeBytes("Location: " + redirectPath + System.lineSeparator());
            for (Map.Entry<String, String> header : headers.entrySet()) {
                dos.writeBytes(String.format("%s: %s" + System.lineSeparator(), header.getKey(), header.getValue()));
            }
            dos.writeBytes(System.lineSeparator());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void methodNotAllowed() {
        try {
            dos.writeBytes("HTTP/1.1 405 Method Not Allowed " + System.lineSeparator());
            dos.writeBytes("Content-Type: text/html;charset=utf-8" + System.lineSeparator());
            dos.writeBytes(System.lineSeparator());
            dos.writeBytes("<h1>405 Try another method</h1>");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void notFound() {
        try {
            dos.writeBytes("HTTP/1.1 404 Not Found " + System.lineSeparator());
            dos.writeBytes("Content-Type: text/html;charset=utf-8" + System.lineSeparator());
            dos.writeBytes(System.lineSeparator());
            dos.writeBytes("<h1>404 Not found</h1>");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
