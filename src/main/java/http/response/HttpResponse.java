package http.response;

import utils.FileIoUtils;

import java.io.DataOutputStream;
import java.io.IOException;
import java.io.OutputStream;

public class HttpResponse {
    private final DataOutputStream dos;

    public HttpResponse(OutputStream outputStream) {
        this.dos = new DataOutputStream(outputStream);
    }

    private static String appendBasePath(String path) {
        String baseUrl = "./static";
        if (path.contains(".html") || path.contains("favicon.ico")) {
            baseUrl = "./templates";
        }
        return baseUrl + path;
    }

    public void forward(String path) throws IOException {
        byte[] body = FileIoUtils.loadFileFromClasspath(appendBasePath(path));
        responseHeader(path, body);
        responseBody(body);
    }

    private void responseHeader(String path, byte[] body) throws IOException {
        dos.writeBytes("HTTP/1.1 200 OK \r\n");
        dos.writeBytes("Content-Type: " + findContentType(path) + ";charset=utf-8\r\n");
        dos.writeBytes("Content-Length: " + body.length + "\r\n");
        dos.writeBytes("\r\n");
    }

    private void responseBody(byte[] body) throws IOException {
        dos.write(body, 0, body.length);
        dos.flush();
    }

    private String findContentType(String path) {
        if (path.contains(".css")) {
            return "text/css";
        }
        return "text/html";
    }

    public void sendRedirect(String redirectPath) {
        try {
            dos.writeBytes("HTTP/1.1 302 FOUND \r\n");
            dos.writeBytes("Location: " + redirectPath + "\r\n");
            dos.writeBytes("\r\n");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
