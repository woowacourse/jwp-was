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

    public void forward(String path) throws IOException {
        byte[] body = FileIoUtils.loadFileFromClasspath(path);
        responseHeader(path, body);
        responseBody(body);
    }

    private void responseHeader(String path, byte[] body) throws IOException {
        dos.writeBytes("HTTP/1.1 200 OK " + System.lineSeparator());
        dos.writeBytes("Content-Type: " + findContentType(path) + ";charset=utf-8" + System.lineSeparator());
        dos.writeBytes("Content-Length: " + body.length + System.lineSeparator());
        dos.writeBytes(System.lineSeparator());
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
            dos.writeBytes("HTTP/1.1 302 FOUND " + System.lineSeparator());
            dos.writeBytes("Location: " + redirectPath + System.lineSeparator());
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
