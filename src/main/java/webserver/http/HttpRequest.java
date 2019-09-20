package webserver.http;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import utils.io.NetworkIO;
import utils.parser.KeyValueParserFactory;
import webserver.http.headerfields.*;

import java.util.Collections;
import java.util.Map;
import java.util.Optional;

public class HttpRequest {
    private static final Logger logger = LoggerFactory.getLogger(HttpRequest.class);

    private final HttpMethod method;
    private final HttpPath path;
    private final HttpVersion version;
    private final Map<String, String> headerFields;
    private final Map<String, String> params;

    public static Optional<HttpRequest> deserialize(NetworkIO io) {
        final String[] requestLine = io.readLine().split("\\s+");
        return HttpMethod.of(requestLine[0]).flatMap(method ->
            HttpVersion.of(requestLine[2]).map(version ->
                new HttpRequest(
                        method,
                        new HttpPath(requestLine[1]),
                        version,
                        parseHeaderFields(io),
                        parseParams(method, requestLine[1], io)
                )
            )
        );
    }

    private static Map<String, String> parseHeaderFields(NetworkIO io) {
        return KeyValueParserFactory.httpHeaderFieldsParser().toMap(io.readWhile(line -> line.length() > 0));
    }

    private static Map<String, String> parseParams(HttpMethod method, String fullPath, NetworkIO io) {
        String params = "";
        if (method == HttpMethod.GET && fullPath.contains("?")) {
            params = fullPath.split("\\?")[1];
        } else if (!io.isEOF()) {
            params = io.readLine();
        }
        return KeyValueParserFactory.queryStringParser().toMap(params);
    }

    private HttpRequest(
            HttpMethod method,
            HttpPath path,
            HttpVersion version,
            Map<String, String> headerFields,
            Map<String, String> params
    ) {
        logger.debug(
                "\r\n{}: {} {}\r\n{}\r\n{}",
                method,
                path,
                version,
                debugString(headerFields),
                debugString(params)
        );

        this.method = method;
        this.path = path;
        this.version = version;
        this.headerFields = Collections.unmodifiableMap(headerFields);
        this.params = Collections.unmodifiableMap(params);
    }

    private String debugString(Map<String, String> x) {
        final StringBuilder acc = new StringBuilder();
        x.forEach((key, value) -> acc.append(String.format("%s: %s\r\n", key, value)));
        return acc.toString();
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

    public Optional<HttpConnection> connection() {
        return HttpConnection.of(headerFields.get("Connection"));
    }

    public String getField(String key) {
        return this.headerFields.get(key);
    }

    public String getParam(String key) {
        return this.params.get(key);
    }
}