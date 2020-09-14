package webserver.http.request;

import exception.InvalidHttpMessageException;
import exception.InvalidUriException;
import utils.StringUtils;
import webserver.http.QueryString;

public class HttpUri {
    private static final String URI_QUERY_STRING_DELIMITER = "\\?";

    private final HttpResourceUri httpResourceUri;
    private final QueryString queryString;

    private HttpUri(HttpResourceUri httpResourceUri, QueryString queryString) {
        this.httpResourceUri = httpResourceUri;
        this.queryString = queryString;
    }

    public static HttpUri from(String uri) {
        StringUtils.validateNonNullAndNotEmpty(() -> new InvalidHttpMessageException(uri), uri);
        validateFirstCharacterIsSlash(uri);

        String[] tokens = uri.split(URI_QUERY_STRING_DELIMITER);
        String resourceUri = tokens[0];
        String queryString = tokens.length == 2 ? tokens[1] : "";

        return new HttpUri(HttpResourceUri.from(resourceUri), QueryString.from(queryString));
    }

    private static void validateFirstCharacterIsSlash(String uri) {
        if (!uri.startsWith("/")) {
            throw new InvalidUriException(uri);
        }
    }

    public byte[] readFile() {
        return this.httpResourceUri.readFile();
    }

    public String getContentType() {
        return this.httpResourceUri.getContentType();
    }

    public String getParameterValue(String parameterKey) {
        return this.queryString.getParameterValue(parameterKey);
    }

    public QueryString getQueryString() {
        return this.queryString;
    }
}
