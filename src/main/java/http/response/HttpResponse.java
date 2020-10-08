package http.response;

import http.HttpStatusCode;
import http.request.HttpRequest;
import utils.FileIoUtils;

import java.io.DataOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.net.URISyntaxException;
import java.util.HashMap;
import java.util.Map;

import static com.google.common.net.HttpHeaders.ACCEPT;

public class HttpResponse {
    public static final String HTTP_VERSION = "HTTP/1.1";
    public static final String CONTENT_TYPE = "Content-Type";
    public static final String CONTENT_LENGTH = "Content-Length";
    public static final String LOCATION = "Location";
    public static final String SET_COOKIE = "Set-Cookie";

    private final DataOutputStream dos;
    private final Map<String, String> header = new HashMap<>();

    public HttpResponse(OutputStream outputStream) {
        this.dos = new DataOutputStream(outputStream);
    }

    public void forward(HttpRequest httpRequest) throws IOException, URISyntaxException {
        byte[] body = FileIoUtils.loadFileFromClasspath(httpRequest.getPath());
        header.put(CONTENT_TYPE, findContentType(httpRequest));
        header.put(CONTENT_LENGTH, String.valueOf(body.length));

        responseHeader(HttpStatusCode.OK);
        responseBody(body);
    }

    private String findContentType(HttpRequest httpRequest) {
        return httpRequest.getHeader(ACCEPT).split(",")[0];
    }

    public void responseHeader(HttpStatusCode statusCode) throws IOException {
        dos.writeBytes(String.format("%s %s" + System.lineSeparator(), HTTP_VERSION, statusCode.findCodeAndMessage()));
        for (Map.Entry<String, String> header : header.entrySet()) {
            dos.writeBytes(String.format("%s: %s" + System.lineSeparator(), header.getKey(), header.getValue()));
        }
        dos.writeBytes(System.lineSeparator());
    }

    private void responseBody(byte[] body) throws IOException {
        dos.write(body, 0, body.length);
        dos.flush();
    }

    public void forward(HttpRequest httpRequest, byte[] body) throws IOException {
        header.put(CONTENT_TYPE, findContentType(httpRequest));
        header.put(CONTENT_LENGTH, String.valueOf(body.length));

        responseHeader(HttpStatusCode.OK);
        responseBody(body);
    }

    public void sendRedirect(String redirectPath) {
        try {
            putHeader(LOCATION, redirectPath);
            responseHeader(HttpStatusCode.FOUND);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void methodNotAllowed() {
        try {
            header.put(CONTENT_TYPE, "text/html;charset=utf-8");
            responseHeader(HttpStatusCode.METHOD_NOT_ALLOWED);
            String body = "<h1>405 Try another method</h1>";
            responseBody(body.getBytes());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void notFound() {
        try {
            header.put(CONTENT_TYPE, "text/html;charset=utf-8");
            responseHeader(HttpStatusCode.NOT_FOUND);
            String body = "<h1>404 Not found</h1>";
            responseBody(body.getBytes());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void putHeader(String key, String value) {
        header.put(key, value);
    }
}
