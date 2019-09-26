package webserver.controller.response;

import utils.FileIoUtils;
import webserver.controller.request.HttpRequest;
import webserver.controller.request.MimeType;
import webserver.controller.request.header.HttpMethod;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public class HttpResponse {
    private static final String STATIC_FILE_PATH = "./static/";
    private static final String NON_STATIC_FILE_PATH = "./templates/";
    private HttpStatus httpStatus;
    private String version;
    private HashMap<String, String> headerFields;
    private Optional<byte[]> body;

    public HttpResponse(HttpRequest httpRequest) throws IOException, URISyntaxException {
        headerFields = new HashMap<>();
        setResponseLine(httpRequest, HttpStatus.OK);
    }

    private void setResponseLine(HttpRequest httpRequest, HttpStatus httpStatus) throws IOException, URISyntaxException {
        HttpMethod httpMethod = httpRequest.getHttpMethod();
        this.httpStatus = httpStatus;
        this.version = httpRequest.getVersion();
        if (httpMethod != HttpMethod.POST && httpMethod != HttpMethod.PUT) {
            this.body = Optional.of(getResponseBody(httpRequest));
        }
    }

    public void responseOK(HttpRequest httpRequest) {
        headerFields.put("Content-Type", httpRequest.getMimeType().getMimeType() + ";charset=utf-8\r\n");
        headerFields.put("Content-Length", String.valueOf(body.get().length));
    }

    private byte[] getResponseBody(HttpRequest httpRequest) throws IOException, URISyntaxException {
        MimeType mimeType = httpRequest.getMimeType();
        String path = httpRequest.getPath();
        if (mimeType == MimeType.HTML || mimeType == MimeType.ICO) {
            return FileIoUtils.loadFileFromClasspath(NON_STATIC_FILE_PATH + path);
        }
        return FileIoUtils.loadFileFromClasspath(STATIC_FILE_PATH + path);
    }

    public void responseBadRequest(String errorMessage) {
        setHttpStatus(HttpStatus.BAD_REQUEST);
        headerFields.put("Server", "jwp-was");
        headerFields.put("Connection", "close");
        headerFields.put("message", errorMessage);
    }

    public void sendRedirect(String saveRedirectUrl) {
        setHttpStatus(HttpStatus.FOUND);
        headerFields.put("Location", saveRedirectUrl);
        headerFields.put("Connection", "close");
    }

    public HttpStatus getHttpStatus() {
        return httpStatus;
    }

    public String getVersion() {
        return version;
    }

    public Map<String, String> getHeaderFields() {
        return Collections.unmodifiableMap(headerFields);
    }

    public Optional<byte[]> getBody() {
        return body;
    }

    public void setHttpStatus(HttpStatus httpStatus) {
        this.httpStatus = httpStatus;
    }
}
