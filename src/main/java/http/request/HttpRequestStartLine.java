package http.request;

import http.HttpVersion;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.UnsupportedEncodingException;

public class HttpRequestStartLine {
    private static final Logger log = LoggerFactory.getLogger(HttpRequestStartLine.class);
    private static final String LINE_SPLITTER = " ";
    private static final String QUERY_SPLITTER = "\\?";

    private final HttpMethodType httpMethodType;
    private final String path;
    private final HttpRequestParameter httpRequestParameters;
    private final HttpVersion httpVersion;

    private HttpRequestStartLine(HttpMethodType httpMethodType, String path, HttpRequestParameter httpRequestParameters, HttpVersion httpVersion) {
        this.httpMethodType = httpMethodType;
        this.path = path;
        this.httpRequestParameters = httpRequestParameters;
        this.httpVersion = httpVersion;
    }

    public static HttpRequestStartLine of(String startLine) throws UnsupportedEncodingException {
        log.debug(startLine);
        String[] startLineValues = startLine.split(LINE_SPLITTER);

        HttpMethodType httpMethodType = HttpMethodType.valueOf(startLineValues[0]);

        String url = startLineValues[1];
        String[] splitUrl = url.split(QUERY_SPLITTER);

        String path = splitUrl[0];
        HttpRequestParameter httpRequestParameters = parseHttpRequestParameter(splitUrl);

        HttpVersion httpVersion = HttpVersion.findVersion(startLineValues[2]);

        return new HttpRequestStartLine(httpMethodType, path, httpRequestParameters, httpVersion);
    }

    private static HttpRequestParameter parseHttpRequestParameter(String[] splitUrl) throws UnsupportedEncodingException {
        HttpRequestParameter httpRequestParameters = HttpRequestParameter.EMPTY_PARAMETER;
        if (1 < splitUrl.length) {
            httpRequestParameters = HttpRequestParameter.of(splitUrl[1]);
        }
        return httpRequestParameters;
    }

    HttpMethodType getHttpMethodType() {
        return httpMethodType;
    }

    String getPath() {
        return path;
    }

    String getParameter(String key) {
        return httpRequestParameters.getParameter(key);
    }

    HttpVersion getHttpVersion() {
        return httpVersion;
    }

    boolean hasParameters() {
        return !httpRequestParameters.equals(HttpRequestParameter.EMPTY_PARAMETER);
    }
}
