package webserver;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import utils.IOUtils;
import utils.StringUtils;

public class HttpRequest {
    public static final String PATH = "path";
    private static final String PARAMETER_DELIMITER = "\\?";
    private static final String FIRST_LINE_DELIMITER = " ";
    private static final int HTTP_METHOD_INDEX = 0;
    private static final int URL_INDEX = 1;
    private static final int HTTP_VERSION_INDEX = 2;
    private static final int PATH_INDEX = 0;
    private static final int PARAMETER_INDEX = 1;

    private enum HttpMethod {
        GET, POST;

        public boolean isPost() {
            return this == POST;
        }
    }

    private final HttpMethod httpMethod;
    private final String path;
    private final Map<String, String> parameters;
    private final Map<String, String> httpHeaders;

    private HttpRequest(HttpMethod httpMethod, String path, Map<String, String> parameters,
            Map<String, String> httpHeaders) {
        this.httpMethod = httpMethod;
        this.path = path;
        this.parameters = parameters;
        this.httpHeaders = httpHeaders;
    }

    public static HttpRequest from(BufferedReader bufferedReader) throws IOException {
        String line = bufferedReader.readLine();
        String[] firstLine = line.split(FIRST_LINE_DELIMITER);

        HttpMethod httpMethod = HttpMethod.valueOf(firstLine[HTTP_METHOD_INDEX]);

        String[] urlTokens = firstLine[URL_INDEX].split(PARAMETER_DELIMITER);
        String path = urlTokens[PATH_INDEX];

        Map<String, String> parameters = parseParameters(urlTokens);

        Map<String, String> httpsHeaders = new HashMap<>();
        httpsHeaders.put("Http-Version", firstLine[HTTP_VERSION_INDEX]);
        httpsHeaders.putAll(IOUtils.readRequestHeaders(bufferedReader));

        return new HttpRequest(httpMethod, path, parameters, httpsHeaders);
    }

    private static Map<String, String> parseParameters(String[] urlTokens) {
        Map<String, String> parameters = new HashMap<>();

        if (hasParameter(urlTokens)) {
            parameters.putAll(StringUtils.readParameters(urlTokens[PARAMETER_INDEX]));
        }
        return parameters;
    }

    private static boolean hasParameter(String[] urlTokens) {
        return urlTokens.length > 1;
    }

    public String getPath() {
        return path;
    }

    public Map<String, String> getParameters() {
        return parameters;
    }
}
