package http;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import utils.NetworkIO;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

public class HttpRequest {
    private static final Logger logger = LoggerFactory.getLogger(HttpRequest.class);

    private final HttpMethod method;
    private final HttpPath path;
    private final HttpVersion version;
    private final Map<String, String> otherFields;
    private final Map<String, String> params;

    public static Optional<HttpRequest> parse(NetworkIO io) {
        if (io.hasNext()) {
            final String requestLine = io.readLine();
            logger.debug("test : {}", requestLine);
            final String[] requestLineTokens = requestLine.split("\\s+");
            return HttpMethod.of(requestLineTokens[0]).flatMap(method ->
                HttpPath.of(requestLineTokens[1]).flatMap(path ->
                    HttpVersion.of(requestLineTokens[2]).map(version ->
                        new HttpRequest(method, path, version, parseOtherFields(io), parseParams(method, requestLineTokens[1], io))
                    )
                )
            );
        }
        return Optional.empty();
    }

    private static Map<String, String> parseOtherFields(NetworkIO io) {
        return new HashMap<String, String>() {{
            while (io.hasNext()) {
                final String[] tokens = io.readLine().split(": ");
                if (tokens.length < 2) {
                    break;
                }
                put(tokens[0], tokens[1]);
            }
        }};

    }

    private static Map<String, String> parseParams(HttpMethod method, String path, NetworkIO io) {
        String params = null;
        if (method == HttpMethod.GET && path.contains("?")) {
            params = path.split("\\?")[1];
        } else if (io.hasNext()) {
            params = io.readLine();
        }
        return parseKeyValueParams(params);
    }

    private static Map<String, String> parseKeyValueParams(String params) {
        return (params != null)
                ? Arrays.stream(params.split("&"))
                        .map(x -> Arrays.asList(x.split("=")))
                        .collect(Collectors.toMap(x -> x.get(0), x -> x.get(1)))
                : new HashMap<>();
    }

    private HttpRequest(
            HttpMethod method,
            HttpPath path,
            HttpVersion version,
            Map<String, String> otherFields,
            Map<String, String> params
    ) {
        this.method = method;
        this.path = path;
        this.version = version;
        this.otherFields = otherFields;
        this.params = params;
    }

    public HttpPath path() {
        return this.path;
    }

    public String getParam(String key) {
        return this.params.get(key);
    }
}