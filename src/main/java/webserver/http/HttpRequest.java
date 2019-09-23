package webserver.http;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import utils.StringUtils;
import utils.io.NetworkIO;
import utils.parser.KeyValueParserFactory;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public class HttpRequest {
    private static final Logger logger = LoggerFactory.getLogger(HttpRequest.class);

    private final HttpMethod method;
    private final HttpPath path;
    private final HttpVersion version;
    private final HttpHost host;
    private final HttpContentType contentType;
    private final HttpConnection connection;
    private final Map<String, String> otherHeaderFields;
    private final Map<String, String> params;
    private final String body;

    public static Optional<HttpRequest> deserialize(NetworkIO io) {
        final String[] startLine = io.readLine().split("\\s+");
        return (startLine.length != 3)
                ? Optional.empty()
                : HttpMethod.of(startLine[0]).flatMap(method ->
                    HttpVersion.of(startLine[2]).map(version -> {
                        final HttpPath path = new HttpPath(startLine[1]);
                        final Map<String, String> headerFields =
                                KeyValueParserFactory.httpHeaderFieldsParser().interpret(io.readLinesWhileNotEmpty());
                        final HttpHost host = HttpHost.of(
                                headerFields.remove(toFieldName(HttpHost.class))
                        ).orElse(null);
                        final HttpContentType contentType = HttpContentType.of(
                                headerFields.remove(toFieldName(HttpContentType.class))
                        ).orElse(null);
                        final HttpConnection connection = HttpConnection.of(
                                headerFields.remove(toFieldName(HttpConnection.class))
                        ).orElse(null);
                        if (method == HttpMethod.GET && startLine[1].contains("?")) {
                            return new HttpRequest(
                                    method, path, version,
                                    host, contentType, connection, headerFields,
                                    KeyValueParserFactory.queryStringParser().interpret(startLine[1].split("\\?")[1]),
                                    null
                            );
                        }
                        if (method == HttpMethod.POST || method == HttpMethod.PUT) {
                            if (contentType.mimeType() == HttpMimeType.APPLICATION_X_WWW_FORM_URLENCODED) {
                                return new HttpRequest(
                                        method, path, version,
                                        host, contentType, connection, headerFields,
                                        KeyValueParserFactory.queryStringParser().interpret(io.readAllLeft()),
                                        null
                                );
                            }
                        }
                        return new HttpRequest(
                                method, path, version,
                                host, contentType, connection, headerFields,
                                new HashMap<>(),
                                null
                        );
                    })
                );
    }

    private HttpRequest(
            HttpMethod method,
            HttpPath path,
            HttpVersion version,
            HttpHost host,
            HttpContentType contentType,
            HttpConnection connection,
            Map<String, String> otherHeaderFields,
            Map<String, String> params,
            String body
    ) {
        this.method = method;
        this.path = path;
        this.version = version;
        this.host = host;
        this.contentType = contentType;
        this.connection = connection;
        this.otherHeaderFields = Collections.unmodifiableMap(otherHeaderFields);
        this.params = Collections.unmodifiableMap(params);
        this.body = body;

        logger.debug(
                String.format(
                        "Request:\r\n%s %s %s\r\n%s%s%s%s%s%s",
                        method, path, version,
                        toDebugString(this.host),
                        toDebugString(this.contentType),
                        toDebugString(this.connection),
                        toDebugString(otherHeaderFields),
                        toDebugString(params),
                        (body != null ? "\r\n" + body : "")
                )
        );
    }

    private static String toFieldName(Class headerFieldClass) {
        return StringUtils.pascalToKebobCase(headerFieldClass.getSimpleName().split("Http")[1]);
    }

    private String toDebugString(HttpHeaderField headerField) {
        return Optional.ofNullable(headerField).map(x ->
                toFieldName(headerField.getClass()) + ": " + x + "\r\n"
        ).orElse("");
    }

    private String toDebugString(Map<String, String> x) {
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
        return Optional.ofNullable(this.connection);
    }

    public String getField(String key) {
        return this.otherHeaderFields.get(key);
    }

    public String getParam(String key) {
        return this.params.get(key);
    }
}