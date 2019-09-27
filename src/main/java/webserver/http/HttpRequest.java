package webserver.http;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import utils.io.NetworkIO;
import utils.parser.HttpHeaderFieldsParser;
import utils.parser.KeyValueParser;
import utils.parser.QueryStringParser;
import webserver.http.exception.InvalidHttpTypeException;
import webserver.http.headerfields.*;

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

    private final HttpMethod method;
    private final HttpPath path;
    private final HttpVersion version;
    private final HttpHeaderFields headerFields;
    private final Map<String, String> params;

    private HttpRequest(HttpMethod method,
                        HttpPath path,
                        HttpVersion version,
                        HttpHeaderFields headerFields,
                        Map<String, String> params) {
        logger.debug("\r\n{}: {} {}\r\n{}\r\n{}", method, path, version, headerFields.debugString(), KeyValueParser.debugString(params));

        this.method = method;
        this.path = path;
        this.version = version;
        this.headerFields = headerFields;
        this.params = Collections.unmodifiableMap(params);
    }

    public static Optional<HttpRequest> deserialize(NetworkIO io) {
        try {
            final String[] requestLine = io.readLine().split("\\s+");
            HttpMethod method = HttpMethod.of(requestLine[METHOD_INDEX]);
            HttpPath path = HttpPath.of(requestLine[PATH_INDEX]);
            HttpVersion version = HttpVersion.of(requestLine[VERSION_INDEX]);

            HttpHeaderFields headerFields = HttpHeaderFields.init(parseHeaderFields(io));
            Map<String, String> params = parseParams(method, path.toString(), io, headerFields.contentLength());

            return Optional.of(new HttpRequest(method, path, version, headerFields, params));
        } catch (InvalidHttpTypeException e) {
            logger.debug(e.getMessage());
            return Optional.empty();
        }
    }

    private static Map<String, String> parseHeaderFields(NetworkIO io) {
        return HttpHeaderFieldsParser.toMap(io.readWhile(line -> line.length() > ZERO));
    }

    public static Map<String, String> parseParams(HttpMethod method, String fullPath, NetworkIO io, String contentLength) {
        String params = paramsLine(method, fullPath, io, contentLength);
        return QueryStringParser.toMap(params);
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

    public HttpMethod method() {
        return this.method;
    }

    public HttpPath path() {
        return this.path;
    }

    public HttpVersion version() {
        return this.version;
    }

    public HttpConnection connection() {
        return headerFields.connection().orElse(null);
    }

    public String cookie() {
        return headerFields.value("Cookie");
    }

    public String getParam(String key) {
        return this.params.get(key);
    }
}