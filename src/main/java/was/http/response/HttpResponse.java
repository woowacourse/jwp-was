package was.http.response;

import was.http.HttpStatus;
import was.http.MimeType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import was.utils.FileIoUtils;

import java.util.HashMap;
import java.util.Map;

public class HttpResponse {
    private static final Logger LOGGER = LoggerFactory.getLogger(HttpResponse.class);

    private String httpVersion;
    private HttpStatus httpStatus;
    private final Map<String, String> headers = new HashMap<>();
    private final Map<String, String> cookies = new HashMap<>();
    private byte[] body;

    public void forward(String resource) {
        forward(resource, HttpStatus.OK);
    }

    public void forward(String resource, HttpStatus httpStatus) {
        byte[] body = FileIoUtils.loadFileFromClasspath(resource);
        setStatus(httpStatus);
        setHeader("Content-Type", MimeType.getType(FileIoUtils.getFileExtension(resource)));
        setHeader("Content-Length", String.valueOf(body.length));
        setBody(body);
    }

    public void sendRedirect(String location) {
        sendRedirect(location, HttpStatus.FOUND);
    }

    public void sendRedirect(String location, HttpStatus httpStatus) {
        setStatus(httpStatus);
        setHeader("Location", location);
        setBody(null);
    }

    public void setHttpVersion(String httpVersion) {
        this.httpVersion = httpVersion;
    }

    public void setStatus(HttpStatus httpStatus){
        this.httpStatus = httpStatus;
    }

    public void setHeader(String key, String value) {
        headers.put(key, value);
    }

    public void setBody(byte[] body) {
        this.body = body;
    }

    public String getHttpVersion() {
        return httpVersion;
    }

    public HttpStatus getHttpStatus() {
        return httpStatus;
    }

    public Map<String, String> getHeaders() {
        return headers;
    }

    public byte[] getBody() {
        return body;
    }

    public void setCookie(String key, String value) {
        this.cookies.put(key, value);
    }

    public Map<String, String> getCookies() {
        return this.cookies;
    }

    public boolean hasCookies() {
        return !cookies.isEmpty();
    }
}
