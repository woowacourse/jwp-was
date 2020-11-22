package kr.wootecat.dongle.model.http.request;

import static java.lang.String.*;

import java.util.HashMap;

import kr.wootecat.dongle.model.http.exception.IllegalRequestDataFormatException;

public class Url {

    static final Url INTERNAL_ERROR_PAGE_URL = Url.from("/internal-error.html");

    private static final String QUERY_PARAMETER_DELIMITER = "\\?";

    private static final int ONLY_PATH_SIZE = 1;
    private static final int QUERY_PAIR_SIZE = 2;

    private final Path path;
    private final QueryParameters queryParameters;

    private Url(Path path) {
        this(path, new QueryParameters(new HashMap<>()));
    }

    private Url(Path path, QueryParameters queryParameters) {
        this.path = path;
        this.queryParameters = queryParameters;
    }

    public static Url from(String urlPath) {
        String[] uriQueryPair = urlPath.split(QUERY_PARAMETER_DELIMITER);
        if (uriQueryPair.length > QUERY_PAIR_SIZE) {
            throw new IllegalRequestDataFormatException(format("유효한 URL 형식이 아닙니다.: %s", urlPath));
        }
        Path path = new Path(uriQueryPair[0]);

        if (uriQueryPair.length == ONLY_PATH_SIZE) {
            return new Url(path);
        }
        QueryParameters queryParameters = QueryParameters.from(uriQueryPair[1]);

        return new Url(path, queryParameters);
    }

    public boolean isParamEmpty() {
        return queryParameters.isEmpty();
    }

    public boolean isFileRequest() {
        return path.isStaticPath();
    }

    public String getPath() {
        return path.getPath();
    }

    public String getParameter(String name) {
        return queryParameters.get(name);
    }
}
