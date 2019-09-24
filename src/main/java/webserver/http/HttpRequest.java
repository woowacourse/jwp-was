package webserver.http;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import utils.io.NetworkIO;
import utils.parser.KeyValueParserFactory;
import webserver.http.exception.InvalidHttpTypeException;
import webserver.http.headerfields.HttpConnection;
import webserver.http.headerfields.HttpMethod;
import webserver.http.headerfields.HttpPath;
import webserver.http.headerfields.HttpVersion;

import java.io.IOException;
import java.util.Collections;
import java.util.Map;
import java.util.Optional;

public class HttpRequest {
    private static final Logger logger = LoggerFactory.getLogger(HttpRequest.class);

    private static final int METHOD_INDEX = 0;
    private static final int PATH_INDEX = 1;
    private static final int VERSION_INDEX = 2;
    private static final int ZERO = 0;

    private static final String CONTENT_LENGTH = "Content-Length";
    private static final String CONNECTION = "Connection";

    private final HttpMethod method;
    private final HttpPath path;
    private final HttpVersion version;
    private final Map<String, String> headerFields;
    private final Map<String, String> params;

    private HttpRequest(HttpMethod method,
                        HttpPath path,
                        HttpVersion version,
                        Map<String, String> headerFields,
                        Map<String, String> params) {
        logger.debug("\r\n{}: {} {}\r\n{}\r\n{}", method, path, version, debugString(headerFields), debugString(params));

        this.method = method;
        this.path = path;
        this.version = version;
        this.headerFields = Collections.unmodifiableMap(headerFields);
        this.params = Collections.unmodifiableMap(params);
    }

    public static Optional<HttpRequest> deserialize(NetworkIO io) {
        try {
            final String[] requestLine = io.readLine().split("\\s+");
            HttpMethod method = HttpMethod.of(requestLine[METHOD_INDEX]);
            HttpPath path = HttpPath.of(requestLine[PATH_INDEX]);
            HttpVersion version = HttpVersion.of(requestLine[VERSION_INDEX]);

            Map<String, String> headerFields = parseHeaderFields(io);
            Map<String, String> params = parseParams(method, path.toString(), io, headerFields.get(CONTENT_LENGTH));

            return Optional.of(new HttpRequest(method, path, version, headerFields, params));
        } catch (InvalidHttpTypeException e) {
            logger.debug(e.getMessage());
            return Optional.empty();
        }
    }

    private static Map<String, String> parseHeaderFields(NetworkIO io) {
        return KeyValueParserFactory.httpHeaderFieldsParser().toMap(io.readWhile(line -> line.length() > ZERO));
    }

    public static Map<String, String> parseParams(HttpMethod method, String fullPath, NetworkIO io, String contentLength) {
        String params = paramsLine(method, fullPath, io, contentLength);
        return KeyValueParserFactory.queryStringParser().toMap(params);
    }

    private static String paramsLine(HttpMethod method, String fullPath, NetworkIO io, String contentLength) {
        if (method == HttpMethod.GET && fullPath.contains("?")) {
            return fullPath.split("\\?")[1];
        }
        if (method == HttpMethod.POST) {
            return postParamsLine(io, contentLength);
        }
        return "";
    }

    private static String postParamsLine(NetworkIO io, String contentLength) {
        try {
            return io.postBody(Integer.parseInt(contentLength));
        } catch (IOException e) {
            e.printStackTrace();
        }
        throw new IllegalArgumentException();
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
        return HttpConnection.of(headerFields.get(CONNECTION));
    }

    public String getField(String key) {
        return this.headerFields.get(key);
    }

    public String getParam(String key) {
        return this.params.get(key);
    }
}