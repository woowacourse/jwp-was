package was.webserver.http;

import java.util.Map;

public class URL {
    private static final String QUERY_PARAMETER_DELIMITER = "?";
    private static final String QUERY_PARAMETER_REGEX = "\\?";
    private static final int PATH_INDEX = 0;
    private static final int QUERY_PARAMETER_INDEX = 1;

    private String path;
    private Parameters parameters;

    private URL(String path, Parameters parameters) {
        this.path = path;
        this.parameters = parameters;
    }

    public static URL of(String url) {
        if (url.contains(QUERY_PARAMETER_DELIMITER)) {
            String[] urls = url.split(QUERY_PARAMETER_REGEX);
            Parameters parameters = Parameters.notEmptyQueryParameters(urls[QUERY_PARAMETER_INDEX]);
            return new URL(urls[PATH_INDEX], parameters);
        }
        Parameters parameters = Parameters.emptyQueryParameters();
        return new URL(url, parameters);
    }

    public boolean isEndsWith(String path) {
        return this.path.endsWith(path);
    }

    public Map<String, String> getQueryParameters() {
        return parameters.getParameters();
    }

    public String getPath() {
        return path;
    }

    public String getParameter(String key) {
        return parameters.getParameter(key);
    }
}
