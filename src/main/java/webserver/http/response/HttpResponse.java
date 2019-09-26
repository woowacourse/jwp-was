package webserver.http.response;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import webserver.http.Cookie;
import webserver.http.Cookies;
import webserver.http.HttpHeaders;
import webserver.http.HttpStatus;
import webserver.http.request.HttpVersion;

import java.io.DataOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import static webserver.http.HttpHeaders.LOCATION;
import static webserver.http.HttpHeaders.SET_COOKIE;

public class HttpResponse {
    private static final Logger log = LoggerFactory.getLogger(HttpResponse.class);
    public static final HttpVersion DEFAULT_HTTP_VERSION = HttpVersion.HTTP_1_1;
    private static final String DEFAULT_ERROR_MESSAGE = "";

    private final Map<String, Object> attributes = new HashMap<>();
    private final HttpHeaders headers = new HttpHeaders();
    private final OutputStream out;
    private final Cookies cookies;
    private HttpStatus httpStatus;
    private HttpVersion httpVersion = DEFAULT_HTTP_VERSION;
    private String resource;

    public HttpResponse(final OutputStream out) {
        this.out = out;
        this.cookies = new Cookies();
    }

    public void forward(final String resource) {
        forward(resource, HttpStatus.OK);
    }

    public void forward(final String resource, final HttpStatus httpStatus) {
        this.resource = resource;
        setStatus(httpStatus);
    }

    public void sendRedirect(final String location) {
        sendRedirect(location, HttpStatus.FOUND);
    }

    public void sendRedirect(final String location, final HttpStatus httpStatus) {
        setStatus(httpStatus);
        setHeader(LOCATION, location);
    }

    public void sendError(final HttpStatus httpStatus) {
        sendError(httpStatus, DEFAULT_ERROR_MESSAGE);
    }

    public void sendError(final HttpStatus httpStatus, final String message) {
        setStatus(httpStatus);
    }

    public void write() {
        try (DataOutputStream dos = new DataOutputStream(out)) {
            writeStartLine(dos);
            writeHeader(dos);
        } catch (IOException e) {
            log.error(e.getMessage());
        }
    }

    public void write(final byte[] body) {
        try (DataOutputStream dos = new DataOutputStream(out)) {
            writeStartLine(dos);
            writeHeader(dos);
            writeBody(dos, body);
        } catch (IOException e) {
            log.error(e.getMessage());
        }
    }


    private void writeStartLine(final DataOutputStream dos) throws IOException {
        dos.writeBytes(String.format("%s %s %s\n", httpVersion.getHttpVersion(), httpStatus.getCode(), httpStatus.getPhrase()));
    }

    private void writeHeader(final DataOutputStream dos) throws IOException {
        for (String key : headers.keySet()) {
            dos.writeBytes(String.format("%s: %s\n", key, headers.get(key)));
        }
        if (cookies.isNotEmpty()) {
            writeSetCookie(dos);
        }
        dos.writeBytes("\n");
    }

    private void writeSetCookie(final DataOutputStream dos) throws IOException {
        for (final String name : cookies.keySet()) {
            dos.writeBytes(String.format("%s: %s\n", SET_COOKIE, cookies.get(name).parseInfoAsString()));
        }
    }

    private void writeBody(final DataOutputStream dos, final byte[] body) throws IOException {
        if (body != null) {
            dos.write(body, 0, body.length);
        }
    }

    public void addCookie(final Cookie cookie) {
        cookies.add(cookie);
    }

    public void setAttribute(final String key, final Object value) {
        attributes.put(key, value);
    }

    public Map<String, Object> getAttributes() {
        return Collections.unmodifiableMap(attributes);
    }

    public void setHttpVersion(final HttpVersion httpVersion) {
        this.httpVersion = httpVersion;
    }

    public void setHeader(final String name, final String value) {
        headers.put(name, value);
    }

    public void setStatus(final HttpStatus httpStatus) {
        this.httpStatus = httpStatus;
    }

    public String getResource() {
        return resource;
    }

    public HttpStatus getHttpStatus() {
        return httpStatus;
    }

    public String getHeader(final String name) {
        return headers.get(name);
    }

    public HttpVersion getHttpVersion() {
        return httpVersion;
    }
}
