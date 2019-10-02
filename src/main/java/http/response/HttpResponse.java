package http.response;

import http.HttpHeaders;
import http.HttpMimeType;
import http.HttpVersion;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import view.SimpleView;
import view.View;

import java.io.DataOutputStream;
import java.io.IOException;
import java.io.OutputStream;

import static http.HttpHeaders.*;
import static http.HttpMimeType.HTML;
import static http.response.HttpStatus.FOUND;
import static http.response.HttpStatus.OK;

public class HttpResponse {
    private static final Logger log = LoggerFactory.getLogger(HttpResponse.class);
    private static final String COOKIE_DELIMITER = "; ";
    private static final String KEY_VALUE_DELIMITER = "=";

    private HttpVersion version;
    private HttpStatus status;
    private HttpHeaders headers;
    private DataOutputStream dataOutputStream;

    public HttpResponse(HttpVersion version, OutputStream outputStream) {
        this.version = version;
        this.status = OK;
        this.headers = new HttpHeaders();
        this.dataOutputStream = new DataOutputStream(outputStream);
    }

    public void sendRedirect(String location) {
        status = FOUND;
        headers.put(LOCATION, location);

        try {
            dataOutputStream.writeBytes(getHeaderMessage());
        } catch (IOException e) {
            log.debug(e.getMessage());
        }
    }

    public void sendError(HttpStatus status) {
        headers.clear();
        this.status = status;
        sendResponseWithView(new SimpleView(status.getMessage()), HTML);
    }

    public void sendResponseWithView(View view, HttpMimeType mimeType) {
        try {
            byte[] body = view.render();
            if (body != null) {
                headers.put(CONTENT_TYPE, mimeType.toString());
                headers.put(CONTENT_LENGTH, Integer.toString(body.length));
            }
            dataOutputStream.writeBytes(getHeaderMessage());

            if (body != null) {
                dataOutputStream.write(body);
            }
        } catch (IOException e) {
            log.debug(e.getMessage());
        }
    }

    private String getHeaderMessage() {
        return version.toString() + " " + status.getMessage() + "\r\n"
                + headers
                + "\r\n";
    }

    public HttpStatus getStatus() {
        return status;
    }

    public void setHeader(String key, String value) {
        headers.put(key, value);
    }

    public HttpHeaders getHeaders() {
        return headers;
    }

    public void setCookie(String key, String value) {
        String cookie = key + KEY_VALUE_DELIMITER + value;
        if (headers.existHeader(SET_COOKIE)) {
            cookie = headers.getHeader(SET_COOKIE) + COOKIE_DELIMITER + cookie;
        }
        headers.put(SET_COOKIE, cookie);
    }
}
