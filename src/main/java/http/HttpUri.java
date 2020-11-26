package http;

import utils.HttpUtils;

public class HttpUri {
    private static final String DELIMITER = "\\?";
    private static final int PATH_INDEX = 0;
    private static final int QUERY_INDEX = 1;
    private static final int HAS_QUERY = 2;

    private final String path;
    private final QueryParams query;

    private HttpUri(String path, QueryParams query) {
        this.path = path;
        this.query = query;
    }

    public static HttpUri from(String uri) {
        String decodedUrl = HttpUtils.urlDecode(uri);
        String[] tokens = decodedUrl.split(DELIMITER);
        if (tokens.length == HAS_QUERY) {
            return new HttpUri(tokens[PATH_INDEX], QueryParams.parse(tokens[QUERY_INDEX]));
        }
        return new HttpUri(tokens[PATH_INDEX], QueryParams.empty());
    }

    public String getPath() {
        return path;
    }

    public String getParam(String key) {
        return query.getFirst(key);
    }
}
