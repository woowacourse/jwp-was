package webserver.domain.request;

import org.slf4j.Logger;
import utils.IOUtils;

import java.io.IOException;

import static org.slf4j.LoggerFactory.getLogger;

public class Request {
    private static final Logger LOG = getLogger(Request.class);

    private static final String EMPTY = "";
    private static final String SPACE_DELIMITER = " ";
    private static final String QUERY_DELIMITER = "\\?";
    private static final int URL_INDEX = 1;
    private static final int QUERY_INDEX = 0;
    private static final int ZERO = 0;
    private static final int NO_QUERY = 1;

    private final RequestLine requestLine;
    private final RequestHeader requestHeader;
    private final RequestBody requestBody;

    public Request(final IOUtils IOUtils) throws IOException {
        final String[] httpMethodAndPath = IOUtils.iterator().next().split(SPACE_DELIMITER);
        final String[] pathAndQuery = httpMethodAndPath[URL_INDEX].split(QUERY_DELIMITER);

        this.requestLine = new RequestLine(httpMethodAndPath);
        this.requestHeader = new RequestHeader(IOUtils);
        this.requestBody = new RequestBody((pathAndQuery.length == NO_QUERY) ? EMPTY : pathAndQuery[QUERY_INDEX]);

        final int contentLength = requestHeader.getContentLength();
        final String body = (contentLength > ZERO) ? IOUtils.readBody(contentLength) : EMPTY;
        this.requestBody.putQueryBy(body);
    }

    public String getHttpMethod() {
        return requestLine.getHttpMethod();
    }

    public String getPath() {
        return requestLine.getPath();
    }

    public String getHttpVersion() {
        return requestLine.getHttpVersion();
    }

    public RequestHeader getRequestHeader() {
        return requestHeader;
    }

    public RequestBody getRequestBody() {
        return requestBody;
    }

    public String getFieldsValue(final String key) {
        return requestHeader.getFieldsValue(key);
    }

    public String getQueryValue(final String key) {
        return requestBody.getQueryValue(key);
    }

}
