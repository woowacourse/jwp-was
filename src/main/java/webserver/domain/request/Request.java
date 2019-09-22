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

    private final RequestStartLine startLine;
    private final RequestHeader header;
    private final RequestBody body;

    public Request(final IOUtils IOUtils) throws IOException {
        final String[] httpMethodAndPath = IOUtils.iterator().next().split(SPACE_DELIMITER);
        final String[] pathAndQuery = httpMethodAndPath[URL_INDEX].split(QUERY_DELIMITER);

        this.startLine = new RequestStartLine(httpMethodAndPath);
        this.header = new RequestHeader(IOUtils);
        this.body = new RequestBody((pathAndQuery.length == NO_QUERY) ? EMPTY : pathAndQuery[QUERY_INDEX]);

        final int contentLength = header.getContentLength();
        final String body = (contentLength > ZERO) ? IOUtils.readBody(contentLength) : EMPTY;
        this.body.putQueryBy(body);
    }

    public String getHttpMethod() {
        return startLine.getHttpMethod();
    }

    public String getPath() {
        return startLine.getPath();
    }

    public String getHttpVersion() {
        return startLine.getHttpVersion();
    }

    public RequestHeader getHeader() {
        return header;
    }

    public RequestBody getBody() {
        return body;
    }

    public String getFieldsValue(final String key) {
        return header.getFieldsValue(key);
    }

    public String getQueryValue(final String key) {
        return body.getQueryValue(key);
    }

}
