package webserver;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import utils.io.NetworkIO;
import utils.parser.KeyValueParserFactory;
import webserver.httpelement.*;

import java.util.Collections;
import java.util.Map;
import java.util.Optional;

public final class HttpRequest {
    private static final Logger logger = LoggerFactory.getLogger(HttpRequest.class);

    private final HttpMethod method;
    private final HttpPath path;
    private final HttpVersion version;
    private final HttpRequestHeader header;
    private final Map<String, String> params;
    private final String body;

    public static Optional<HttpRequest> deserialize(NetworkIO io) {
        final String[] startLine = io.readLine().split("\\s+");
        if (startLine.length != 3) {
            return Optional.empty();
        }
        return HttpMethod.of(startLine[0]).flatMap(method ->
        HttpPath.of(startLine[1]).flatMap(path ->
        HttpVersion.of(startLine[2]).flatMap(version ->
        HttpRequestHeader.of(
                KeyValueParserFactory.httpHeaderFieldsParser().interpret(
                        io.readLinesWhileNotEmpty()
                )
        ).map(header -> {
            if (method == HttpMethod.GET && startLine[1].contains("?")) {
                return paramsInURL(method, path, version, header, startLine[1].split("\\?")[1]);
            }
            if (method == HttpMethod.POST || method == HttpMethod.PUT) {
                return withBody(method, path, version, header, io);
            }
            return new HttpRequest(method, path, version, header, Collections.emptyMap(), null);
        }))));
    }

    private static HttpRequest paramsInURL(
            HttpMethod method,
            HttpPath path,
            HttpVersion version,
            HttpRequestHeader header,
            String encodedQueryParams
    ) {
        return new HttpRequest(
                method, path, version,
                header,
                KeyValueParserFactory.queryStringParser().interpret(encodedQueryParams),
                null
        );
    }

    private static HttpRequest withBody(
            HttpMethod method,
            HttpPath path,
            HttpVersion version,
            HttpRequestHeader header,
            NetworkIO io
    ) {
        return header.contentType().map(contentType -> {
            if (contentType.mimeType() == HttpMimeType.APPLICATION_X_WWW_FORM_URLENCODED) {
                return new HttpRequest(
                        method, path, version,
                        header,
                        KeyValueParserFactory.queryStringParser().interpret(io.readAllLeft()),
                        null
                );
            }
            //미구현
            if (contentType.mimeType() == HttpMimeType.MULTIPART_FORM_DATA) {
                contentType.boundary();
                return null;
            }
            return null;
        }).orElseGet(() -> new HttpRequest(method, path, version, header, Collections.emptyMap(), io.readAllLeft()));
    }

    private HttpRequest(
            HttpMethod method,
            HttpPath path,
            HttpVersion version,
            HttpRequestHeader header,
            Map<String, String> params,
            String body
    ) {
        this.method = method;
        this.path = path;
        this.version = version;
        this.header = header;
        this.params = Collections.unmodifiableMap(params);
        this.body = body;

        logger.debug(
                "Request:\r\n{} {} {}\r\n{}\r\nParams: {}\r\n\r\n{}\r\n",
                method, path, version,
                header,
                params,
                (body != null ? body : "")
        );
    }

    public HttpMethod method() {
        return this.method;
    }

    public HttpPath path() {
        return this.path;
    }

    public HttpVersion version() {
        return this.version;
    }

    public String getCookieOf(String key) {
        return this.header.getCookieOf(key);
    }

    public Optional<HttpConnection> connection() {
        return this.header.connection();
    }

    public String getParam(String key) {
        return this.params.get(key);
    }
}