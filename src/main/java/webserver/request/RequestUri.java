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

    public RequestUri(String line) {
        absPathAndQuery = HttpRequestUtils.parseAbsPathAndQuery(line);
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

        Map<String, String> queryStrings = Arrays.stream(splitAbsPath[1].trim().split("&"))
            .map(s -> s.split("=", 2))
            .collect(Collectors.toMap(a -> a[0], a -> a.length > 1 ? a[1] : ""));

        return queryStrings;
    }

    public String getAbsPath() {
        return absPath;
    }
    public String getQueryString(String key){
        return queryStrings.get(key);
    }
}
