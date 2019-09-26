package webserver.http.request;

import utils.HttpRequestUtils;

import java.util.Map;

public class RequestUri {
    private static final int ABS_PATH_INDEX = 0;
    private static final String FILE_EXTENSION_DELIMITER = ".";
    private final String absPathAndQuery;
    private final String absPath;
    private final Map<String, String> queryStrings;

    public RequestUri(String pathAndQuery) {
        absPathAndQuery = pathAndQuery;
        absPath = HttpRequestUtils.parseAbsPathAndQuery(absPathAndQuery)[ABS_PATH_INDEX];
        queryStrings = HttpRequestUtils.parseQueryString(absPathAndQuery);
    }

    public String getAbsPath() {
        return absPath;
    }

    public String getQueryString(String key) {
        return queryStrings.get(key);
    }

    public boolean isFile() {
        return absPath.contains(FILE_EXTENSION_DELIMITER);
    }

    public boolean isSameAbsPath(String targetAbsPath){
        return absPath.equals(targetAbsPath);
    }
}
