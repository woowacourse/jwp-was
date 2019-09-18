package webserver.request;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import utils.HttpRequestUtils;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

public class RequestUri {
    private static final Logger log = LoggerFactory.getLogger(RequestUri.class);

    private final String absPathAndQuery;
    private final String absPath;
    private final Map<String, String> queryStrings;

    public RequestUri(String pathAndQuery) {
        absPathAndQuery = pathAndQuery;
        absPath = parseAbsPath(absPathAndQuery);
        queryStrings = parseQueryStrings(absPathAndQuery);
    }

    private String parseAbsPath(String absPathAndQuery) {
        return absPathAndQuery.split("\\?")[0];
    }

    private Map<String, String> parseQueryStrings(String absPath) {
        String[] splitAbsPath = absPath.split("\\?");
        if (splitAbsPath.length != 2) {
            return new HashMap<>();
        }
        Map<String, String> queryStrings = HttpRequestUtils.parseParamToMap(splitAbsPath[1]);
        return queryStrings;
    }

    public String getAbsPath() {
        return absPath;
    }
    public String getQueryString(String key){
        return queryStrings.get(key);
    }
}
