package webserver.controller.response;

import exception.UnregisteredURLException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import utils.FileIoUtils;
import webserver.controller.cookie.HttpCookie;
import webserver.controller.request.HttpRequest;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.StringJoiner;
import java.util.stream.Stream;

import static webserver.controller.LoginController.LOGIN_SUCCESS_INDEX;


public class HttpResponse {
    private static final Logger logger = LoggerFactory.getLogger(HttpResponse.class);
    private static final String DEFAULT_COOKIE_PATH = "/";
    public static final String STATIC_FILE_PATH = "static";
    private HttpStatus httpStatus;
    private String version;
    private Map<String, String> headerFields;
    private List<String> cookieFields;
    private byte[] body;

    private HttpResponse(ResponseBuilder builder) {
        this.httpStatus = builder.httpStatus;
        this.version = builder.version;
        this.headerFields = builder.headerFields;
        this.cookieFields = builder.cookieFields;
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
        if(httpRequest.isFirstRequest()) {
            return HttpResponse.builder()
                .version(httpRequest.getVersion())
                .httpStatus(HttpStatus.OK)
                .setCookie(httpRequest.getCookieFields())
                .contentType(httpRequest.getMimeType().getMimeType())
                .body(body)
                .build();
        }
        return HttpResponse.builder()
            .version(httpRequest.getVersion())
            .httpStatus(HttpStatus.OK)
            .contentType(httpRequest.getMimeType().getMimeType())
            .body(body)
            .build();
    }

    public static HttpResponse badRequest(String message) {
        return HttpResponse.builder()
            .httpStatus(HttpStatus.BAD_REQUEST)
            .connection("close")
            .message(message)
            .build();
    }

    public static HttpResponse sendRedirect(HttpRequest httpRequest, String redirectUrl) {
        return redirectBuild(httpRequest, redirectUrl)
            .build();
    }


    private static ResponseBuilder redirectBuild(HttpRequest httpRequest, String redirectUrl) {
        return HttpResponse.builder()
            .version(httpRequest.getVersion())
            .httpStatus(HttpStatus.FOUND)
            .location(redirectUrl);
    }

    public static HttpResponse loginSuccessRedirect(HttpRequest httpRequest, String redirecUrl) {
        return redirectBuild(httpRequest, redirecUrl)
            .setCookie(httpRequest.getCookieFields())
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

    public boolean hasCookie() {
        return !cookieFields.isEmpty();
    }

    public List<String> getCookieFields() {
        return Collections.unmodifiableList(this.cookieFields);
    }

    private static class ResponseBuilder {
        private String version;
        private HttpStatus httpStatus;
        private Map<String, String> headerFields = new LinkedHashMap<>();
        private List<String> cookieFields = new ArrayList<>();
        private byte[] body = "" .getBytes();

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

        private ResponseBuilder setCookie(Map<String, String> cookieValues) {
            cookieValues.keySet()
                .stream()
                .forEach(key -> cookieFields.add(key+"="+cookieValues.get(key)));
            return this;
        }

        private HttpResponse build() {
            return new HttpResponse(this);
        }
    }
}
