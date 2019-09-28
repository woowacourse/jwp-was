package http.response;

import http.HttpHeaders;
import http.HttpVersion;
import utils.FileIoUtils;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URISyntaxException;

import static http.response.HttpStatus.FOUND;
import static http.response.HttpStatus.OK;

public class HttpResponse {
    public static final String CRLF = "\r\n";
    private static final String DEFAULT_PATH = "./templates";
    private static final String STATIC_PATH = "./static";

    private HttpVersion version;
    private HttpStatus status;
    private HttpHeaders headers;
    private byte[] body;

    private HttpResponse(HttpVersion version, HttpHeaders httpHeaders) {
        this.version = version;
        this.status = OK;
        this.headers = httpHeaders;
    }

    public static HttpResponse of(HttpVersion version) {
        return new HttpResponse(version, new HttpHeaders());
    }

    public void forward(String path) throws IOException, URISyntaxException {
        try {
            body = FileIoUtils.loadFileFromClasspath(DEFAULT_PATH + path);
        } catch (FileNotFoundException e) {
            body = FileIoUtils.loadFileFromClasspath(STATIC_PATH + path);
        }
        // TODO: 2019-09-28 contents type과 contents length 헤더를 만들어줘야 한다
    }

    public void sendRedirect(String location) {
        setStatus(FOUND);
        addHeader("Location", location);
    }

    public void addHeader(String key, String value) {
        headers.put(key, value);
    }

    public String getMessageHeader() {
        StringBuilder sb = new StringBuilder();

        sb.append(version).append(" ").append(status.getMessage());
        if (headers != null) {
            sb.append(CRLF).append(headers.toString());
        }
        return sb.toString();
    }

    public boolean hasBody() {
        return body != null;
    }

    public byte[] getBody() {
        return body;
    }

    public void setStatus(HttpStatus status) {
        this.status = status;
    }

    public void setBody(byte[] body) {
        this.body = body;
    }
}
