package http;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import utils.io.NetworkIO;
import utils.parser.KeyValueParserFactory;

import java.util.Map;
import java.util.Optional;

public class HttpRequest {
    private static final Logger logger = LoggerFactory.getLogger(HttpRequest.class);

    private final HttpMethod method;
    private final HttpPath path;
    private final HttpVersion version;
    private final Map<String, String> otherFields;
    private final Map<String, String> params;

    public static Optional<HttpRequest> deserialize(NetworkIO io, KeyValueParserFactory keyValueParserFactory) {
        final String[] requestLine = io.readLine().split("\\s+");
        return HttpMethod.of(requestLine[0]).flatMap(method ->
            HttpPath.of(requestLine[1]).flatMap(path ->
                HttpVersion.of(requestLine[2]).map(version ->
                    new HttpRequest(
                            method,
                            path,
                            version,
                            parseOtherFields(io, keyValueParserFactory),
                            parseParams(method, requestLine[1], io, keyValueParserFactory)
                    )
                )
            )
        );
    }

    private static Map<String, String> parseOtherFields(NetworkIO io, KeyValueParserFactory keyValueParserFactory) {
        return keyValueParserFactory.httpHeaderFieldsParser().toMap(io.readWhile(line -> line.length() > 0));
    }

    private static Map<String, String> parseParams(
            HttpMethod method,
            String fullPath,
            NetworkIO io,
            KeyValueParserFactory keyValueParserFactory
    ) {
        String params = "";
        if (method == HttpMethod.GET && fullPath.contains("?")) {
            params = fullPath.split("\\?")[1];
        } else if (!io.isEOF()) {
            params = io.readLine();
        }
        return keyValueParserFactory.queryStringParser().toMap(params);
    }

    private HttpRequest(
            HttpMethod method,
            HttpPath path,
            HttpVersion version,
            Map<String, String> otherFields,
            Map<String, String> params
    ) {
        logger.debug(
                "{}: {}\n  fields:\n{}  params:\n{}",
                method,
                path.get(),
                debugString(otherFields),
                debugString(params)
        );

        this.method = method;
        this.path = path;
        this.version = version;
        this.otherFields = otherFields;
        this.params = params;
    }

    private String debugString(Map<String, String> x) {
        final StringBuilder acc = new StringBuilder();
        x.forEach((key, value) -> {
            acc.append("    ");
            acc.append(key);
            acc.append(": ");
            acc.append(value);
            acc.append("\n");
        });
        return acc.toString();
    }

    public HttpPath path() {
        return this.path;
    }

    public String getField(String key) {
        return this.otherFields.get(key);
    }

    public String getParam(String key) {
        return this.params.get(key);
    }
}