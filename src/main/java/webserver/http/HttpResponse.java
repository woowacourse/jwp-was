package webserver.http;

import utils.io.FileIoUtils;
import webserver.http.headerfields.*;

import java.util.HashMap;
import java.util.Map;

public class HttpResponse {
    public static final HttpResponse BAD_REQUEST =
            HttpResponse.builder(HttpContentType.getHttpContentType(MimeType.TEXT_PLAIN))
                    .statusCode(HttpStatusCode.BAD_REQUEST)
                    .build();

    public static final HttpResponse NOT_FOUND =
            HttpResponse.builder(HttpContentType.getHttpContentType(MimeType.TEXT_PLAIN))
                    .statusCode(HttpStatusCode.NOT_FOUND)
                    .build();

    public static final HttpResponse INTERNAL_SERVER_ERROR =
            HttpResponse.builder(HttpContentType.getHttpContentType(MimeType.TEXT_PLAIN))
                    .statusCode(HttpStatusCode.INTERNAL_SERVER_ERROR)
                    .build();

    private final HttpVersion version;
    private final HttpStatusCode statusCode;
    private final HttpContentType contentType;
    private final HttpCookies httpCookies;
    private final HttpHeaderFields headerFields;
    private final String body;

    public static class HttpResponseBuilder {
        private HttpContentType contentType;
        private HttpHeaderFields headerFields;

        private HttpVersion version = HttpVersion.HTTP_1_1;
        private HttpStatusCode statusCode = HttpStatusCode.OK;
        private HttpCookies httpCookies = new HttpCookies();
        private String body = "";

        private Map<String, String> tempFields = new HashMap<>();

        public HttpResponseBuilder(HttpContentType contentType) {
            this.contentType = contentType;
        }

        public HttpResponseBuilder version(HttpVersion httpVersion) {
            this.version = httpVersion;
            return this;
        }

        public HttpResponseBuilder statusCode(HttpStatusCode statusCode) {
            this.statusCode = statusCode;
            return this;
        }

        public HttpResponseBuilder body(String body) {
            this.body = body;
            return this;
        }

        public HttpResponseBuilder connection(HttpConnection connection) {
            tempFields.put("Connection", connection.toString());
            return this;
        }

        public HttpResponseBuilder location(String location) {
            tempFields.put("Location", location);
            return this;
        }

        public HttpResponse build() {
            headerFields = HttpHeaderFields.init(tempFields);
            return new HttpResponse(this);
        }
    }

    public static HttpResponseBuilder builder(HttpContentType contentType) {
        return new HttpResponseBuilder(contentType);
    }

    private HttpResponse(HttpResponseBuilder builder) {
        this.version = builder.version;
        this.statusCode = builder.statusCode;
        this.contentType = builder.contentType;
        this.httpCookies = builder.httpCookies;
        this.headerFields = builder.headerFields;
        this.body = builder.body;
    }

    public static HttpResponse successByBody(HttpRequest request, MimeType mimeType, String body) {
        return HttpResponse.builder(HttpContentType.getHttpContentType(mimeType))
                .version(request.version())
                .connection(request.connection())
                .body(body)
                .build();
    }

    public static HttpResponse successByBody(HttpRequest request, String body) {
        return successByBody(request, MimeType.TEXT_HTML, body);
    }

    public static HttpResponse successByFilePath(HttpRequest request, MimeType mimeType, String filePath) {
        return FileIoUtils.loadFileFromClasspath(filePath)
                .map(body -> successByBody(request, mimeType, body))
                .orElse(INTERNAL_SERVER_ERROR);
    }

    public static HttpResponse successByFilePath(HttpRequest request, String filePath) {
        return successByFilePath(request, MimeType.TEXT_HTML, filePath);
    }

    public static HttpResponse redirection(HttpRequest request, MimeType mimeType, String location) {
        return HttpResponse.builder(HttpContentType.getHttpContentType(mimeType))
                .version(request.version())
                .statusCode(HttpStatusCode.FOUND)
                .connection(request.connection())
                .location(location)
                .build();
    }

    public static HttpResponse redirection(HttpRequest request, String location) {
        return redirection(request, MimeType.TEXT_PLAIN, location);
    }

    public static HttpResponse staticFiles(HttpRequest request) {
        return FileIoUtils.loadFileFromClasspath("./static" + request.path()).map(body ->
                HttpResponse.builder(HttpContentType.extensionToContentType(request.path().extension()))
                        .version(request.version())
                        .connection(request.connection())
                        .body(body)
                        .build()
        ).orElse(HttpResponse.NOT_FOUND);
    }

    public void applySessionCookie(String sessionId) {
        HttpCookie cookie = new HttpCookie();
        cookie.sessionCookie(sessionId);

        httpCookies.add(cookie);
    }

    public void applyLoginCookie(String sessionId, boolean login) {
        HttpCookie cookie = new HttpCookie();
        cookie.loginCookie(login, "/");

        httpCookies.add(cookie);
        applySessionCookie(sessionId);
    }

    public String serializeHeader() {
        final StringBuilder header = new StringBuilder(serializeMandatory());

        headerFields.responseString().ifPresent(header::append);
        httpCookies.responseString().ifPresent(header::append);
        return header.toString();
    }

    private String serializeMandatory() {
        return String.format(
                "%s %d %s\r\n" + "Content-Type: %s\r\n" + "Content-Length: %d\r\n",
                this.version,
                this.statusCode.number(),
                this.statusCode.text(),
                this.contentType,
                this.body.length()
        );
    }

    public String serialize() {
        return serializeHeader() + "\r\n" + this.body;
    }

    @Override
    public String toString() {
        return serialize();
    }
}