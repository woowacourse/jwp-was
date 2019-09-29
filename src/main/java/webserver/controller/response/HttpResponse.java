package webserver.controller.response;

import exception.UnregisteredURLException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import utils.FileIoUtils;
import webserver.controller.HttpCookie;
import webserver.controller.request.HttpRequest;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;


public class HttpResponse {
    private static final Logger logger = LoggerFactory.getLogger(HttpResponse.class);
    public static final String BAD_REQUEST_ERROR_MESSAGE = "올바른 요청이 아닙니다.";
    private static final String DEFAULT_COOKIE_PATH = "/";
    public static final String STATIC_FILE_PATH = "static";
    private HttpStatus httpStatus;
    private String version;
    private Map<String, String> headerFields;
    private byte[] body;

    private HttpResponse(ResponseBuilder builder) {
        this.httpStatus = builder.httpStatus;
        this.version = builder.version;
        this.headerFields = builder.headerFields;
        this.body = builder.body;
    }

    public static ResponseBuilder builder() {
        return new ResponseBuilder();
    }

    public static HttpResponse staticFile(HttpRequest httpRequest, String path) {
        logger.debug(">>path {}", path);
        return FileIoUtils.loadFileFromClasspath(STATIC_FILE_PATH + path)
            .map(b ->
                HttpResponse.builder()
                    .version(httpRequest.getVersion())
                    .contentType(httpRequest.getMimeType().getMimeType())
                    .body(b)
                    .httpStatus(HttpStatus.OK)
                    .build())
            .orElseThrow(UnregisteredURLException::new);
    }

    public static HttpResponse ok(HttpRequest httpRequest, byte[] body) {
        return HttpResponse.builder()
            .version(httpRequest.getVersion())
            .httpStatus(HttpStatus.OK)
            .body(body)
            .contentType(httpRequest.getMimeType().getMimeType())
            .build();
    }

    public static HttpResponse badRequest(HttpRequest httpRequest) {
        return HttpResponse.builder()
            .version(httpRequest.getVersion())
            .httpStatus(HttpStatus.BAD_REQUEST)
            .connection("close")
            .message(BAD_REQUEST_ERROR_MESSAGE)
            .build();
    }

    public static HttpResponse sendRedirect(HttpRequest httpRequest, String redirectUrl, boolean logined) {
        HttpCookie httpCookie = new HttpCookie();
        httpCookie.loginCookie(logined, DEFAULT_COOKIE_PATH);

        return HttpResponse.builder()
            .version(httpRequest.getVersion())
            .httpStatus(HttpStatus.FOUND)
            .location(redirectUrl)
            .setCookie(httpCookie.joinSetCookie())
            .build();
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

    public byte[] getBody() {
        return body;
    }

    public boolean hasBody() {
        return !body.equals("");
    }

    private static class ResponseBuilder {
        private String version;
        private HttpStatus httpStatus;
        private Map<String, String> headerFields = new HashMap<>();
        private byte[] body = "".getBytes();

        private ResponseBuilder httpStatus(HttpStatus httpStatus) {
            this.httpStatus = httpStatus;
            return this;
        }

        private ResponseBuilder version(String version) {
            this.version = version;
            return this;
        }

        private ResponseBuilder body(byte[] body) {
            this.body = body;
            return this;
        }

        private ResponseBuilder contentType(String mimeType) {
            headerFields.put("Content-Type", mimeType + ";charset=utf-8\n");
            return this;
        }

        private ResponseBuilder connection(String value) {
            headerFields.put("Connection", value);
            return this;
        }

        private ResponseBuilder location(String redirectUrl) {
            headerFields.put("Location", redirectUrl);
            return this;
        }

        private ResponseBuilder message(String value) {
            headerFields.put("message", value);
            return this;
        }


        private ResponseBuilder setCookie(String joinSetCookie) {
            headerFields.put("Set-Cookie", joinSetCookie);
            return this;
        }

        private HttpResponse build() {
            return new HttpResponse(this);
        }
    }

}
