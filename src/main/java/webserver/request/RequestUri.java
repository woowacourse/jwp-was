package webserver.request;

import utils.HttpRequestUtils;

import java.util.HashMap;
import java.util.Map;

public class RequestUri {
    private static final int ABS_PATH_QUERY_TOKEN_LENGTH = 2;
    private static final int ABS_PATH_INDEX = 0;
    private static final int QUERY_STRING_INDEX = 1;

    private final String absPathAndQuery;
    private final String absPath;
    private final Map<String, String> queryStrings;

    public RequestUri(String pathAndQuery) {
        absPathAndQuery = pathAndQuery;
        absPath = parseAbsPath();
        queryStrings = parseQueryStrings();
    }

    private String parseAbsPath() {
        return absPathAndQuery.split("\\?")[ABS_PATH_INDEX];
    }

    private Map<String, String> parseQueryStrings() {
        String[] splitAbsPath = absPathAndQuery.split("\\?");
        if (splitAbsPath.length != ABS_PATH_QUERY_TOKEN_LENGTH) {
            return new HashMap<>();
        }
        Map<String, String> queryStrings = HttpRequestUtils.parseParamToMap(splitAbsPath[QUERY_STRING_INDEX]);
        return queryStrings;
    }

    public String getAbsPath() {
        return absPath;
    }

    public String getQueryString(String key) {
        return queryStrings.get(key);
    }
}
