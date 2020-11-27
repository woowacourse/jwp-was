package http;

import java.util.Objects;

public class URI {
    private static final String QUERY_PARAMETER_SPLITTER = "\\?";
    private static final int REQUEST_PATH_INDEX = 0;
    private static final int QUERY_PARAMETER_INDEX = 1;

    private String path;
    private String query;

    public static URI from(String uri) {
        if (Objects.isNull(uri) || uri.isEmpty()) {
            throw new IllegalArgumentException("잘못된 uri입니다.");
        }
        String[] tokens = uri.split(QUERY_PARAMETER_SPLITTER);
        String path = tokens[REQUEST_PATH_INDEX];
        String query = tokens.length > QUERY_PARAMETER_INDEX ? tokens[QUERY_PARAMETER_INDEX] : null;
        return new URI(path, query);
    }

    private URI(String path, String query) {
        this.path = path;
        this.query = query;
    }

    public String getRawPath() {
        return path;
    }

    public String getRawQuery() {
        return query;
    }
}
