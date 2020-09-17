package http.request;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import utils.IOUtils;
import utils.StringUtils;

public class HttpRequest {
    public static final String PATH = "path";
    private static final int PARAMETER_INDEX = 1;
    private static final String CONTENT_LENGTH = "Content-Length";
    private static final Logger logger = LoggerFactory.getLogger(IOUtils.class);

    private final RequestLine requestLine;
    private final Map<String, String> httpHeaders;
    private final String body;

    public HttpRequest(RequestLine requestLine,
            Map<String, String> httpHeaders, String body) {
        this.requestLine = requestLine;
        this.httpHeaders = httpHeaders;
        this.body = body;
    }

    public static HttpRequest from(BufferedReader bufferedReader) throws IOException {
        String firstLine = bufferedReader.readLine();
        logger.debug(firstLine);
        RequestLine requestLine = RequestLine.from(firstLine);

        Map<String, String> httpsHeaders = new HashMap<>(
                IOUtils.readRequestHeaders(bufferedReader));

        String body = null;
        if (httpsHeaders.containsKey(CONTENT_LENGTH)) {
            body = IOUtils.readData(bufferedReader,
                    Integer.parseInt(httpsHeaders.get(CONTENT_LENGTH)));
        }

        return new HttpRequest(requestLine, httpsHeaders, body);
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

    public Map<String, String> getHttpHeaders() {
        return httpHeaders;
    }

    public String getBody() {
        return body;
    }
}
