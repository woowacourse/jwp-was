package webserver.protocol;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import utils.IOUtils;

public final class HttpRequestParser {
    private static final int FIRST_LINE = 0;
    private static final int HTTP_METHOD_INDEX = 0;
    private static final int HTTP_VERSION_INDEX = 2;
    private static final int QUERY_PARAM_INDEX = 1;
    private static final int QUERY_PARAM_NAME_INDEX = 0;
    private static final int QUERY_PARAM_VALUE_INDEX = 1;
    private static final int RESOURCES_INDEX = 1;
    private static final int PATH_INDEX = 0;
    private static final int HEADER_KEY_INDEX = 0;
    private static final int HEADER_VALUE_INDEX = 1;
    private static final int HEADERS_START_INDEX = 1;
    private static final String QUERY_PARAM_REGEX = "&";
    private static final String QUERY_PARAMS_REGEX = "\\?";
    private static final String PARAM_REGEX = "=";
    private static final String HEADER_REGEX = ": ";

    public static HttpRequest parse(final BufferedReader reader) throws IOException {
        final List<String> headerData = IOUtils.readHeaderData(reader);
        final String[] requestLine = headerData.get(FIRST_LINE).split(" ", -1);
        final String[] resources = requestLine[RESOURCES_INDEX].split(QUERY_PARAMS_REGEX, -1);

        final HttpMethod method = HttpMethod.valueOf(requestLine[HTTP_METHOD_INDEX]);
        final String version = requestLine[HTTP_VERSION_INDEX];
        final String path = resources[PATH_INDEX];
        final Map<String, String> queryParams = parseQueryParams(resources);
        final Map<String, String> headers = parseHeaders(headerData);
        final RequestBody body = RequestBodyParser.parse(reader, headers);

        return HttpRequest.builder()
            .method(method)
            .path(path)
            .version(version)
            .queryParams(queryParams)
            .headers(headers)
            .body(body)
            .build();
    }

    private static Map<String, String> parseHeaders(final List<String> header) {
        final Map<String, String> headers = new HashMap<>();
        for (int i = HEADERS_START_INDEX; i < header.size(); i++) {
            final String[] tokens = header.get(i).split(HEADER_REGEX, -1);
            headers.put(tokens[HEADER_KEY_INDEX], tokens[HEADER_VALUE_INDEX]);
        }
        return headers;
    }

    private static Map<String, String> parseQueryParams(final String[] resources) {
        final Map<String, String> queryParams = new HashMap<>();

        if (resources.length > QUERY_PARAM_INDEX) {
            final String[] allQueryParams = resources[QUERY_PARAM_INDEX].split(QUERY_PARAM_REGEX, -1);
            for (final String queryParam : allQueryParams) {
                final String[] nameAndValue = queryParam.split(PARAM_REGEX, -1);
                queryParams.put(nameAndValue[QUERY_PARAM_NAME_INDEX], nameAndValue[QUERY_PARAM_VALUE_INDEX]);
            }
        }

        return queryParams;
    }
}
