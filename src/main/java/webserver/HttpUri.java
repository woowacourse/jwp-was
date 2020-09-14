package webserver;

import exception.FileNotReadableException;
import exception.InvalidUriException;
import utils.FileIoUtils;
import utils.StringUtils;

public class HttpUri {
    private static final String TEMPLATES_PATH = "./templates";
    private static final String URI_QUERY_STRING_DELIMITER = "\\?";

    private final String resourceUri;
    private final QueryString queryString;

    private HttpUri(String resourceUri, QueryString queryString) {
        this.resourceUri = resourceUri;
        this.queryString = queryString;
    }

    public static HttpUri from(String uri) {
        StringUtils.validateNonNullAndNotEmpty(uri);
        validateFirstCharacterIsSlash(uri);

        String[] tokens = uri.split(URI_QUERY_STRING_DELIMITER);
        String resourceUri = tokens[0];
        String queryString = tokens.length == 2 ? tokens[1] : "";

        return new HttpUri(resourceUri, QueryString.from(queryString));
    }

    private static void validateFirstCharacterIsSlash(String uri) {
        if (!uri.startsWith("/")) {
            throw new InvalidUriException(uri);
        }
    }

    public byte[] readFile() {
        String filePath = TEMPLATES_PATH + resourceUri;
        try {
            return FileIoUtils.loadFileFromClasspath(filePath);
        } catch (Exception e) {
            throw new FileNotReadableException(filePath);
        }
    }

    public String getParameterValue(String parameterKey) {
        return this.queryString.getParameterValue(parameterKey);
    }

    public QueryString getQueryString() {
        return this.queryString;
    }
}
