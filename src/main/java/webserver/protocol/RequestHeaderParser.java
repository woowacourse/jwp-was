package webserver.protocol;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public final class RequestHeaderParser {

    private static final int FIRST_LINE = 0;
    private static final int HTTP_METHOD_INDEX = 0;
    private static final int HTTP_VERSION_INDEX = 2;
    private static final int QUERY_PARAM_INDEX = 1;
    private static final int QUERY_PARAM_NAME_INDEX = 0;
    private static final int QUERY_PARAM_VALUE_INDEX = 1;
    private static final int RESOURCES_INDEX = 1;
    private static final int PATH_INDEX = 0;
    private static final String QUERY_PARAM_REGEX = "&";
    private static final String QUERY_PARAMS_REGEX = "\\?";
    private static final String PARAM_REGEX = "=";

    public static RequestHeader parse(final List<String> header) {
        final String[] firstLineSplitResult = header.get(FIRST_LINE).split(" ", -1);
        final String[] resources = firstLineSplitResult[RESOURCES_INDEX].split(QUERY_PARAMS_REGEX, -1);

        final String httpMethod = firstLineSplitResult[HTTP_METHOD_INDEX];
        final String httpVersion = firstLineSplitResult[HTTP_VERSION_INDEX];
        final String path = resources[PATH_INDEX];
        final Map<String, String> queryParams = parseQueryParams(resources);

        return new RequestHeader(httpMethod, path, httpVersion, queryParams);
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
