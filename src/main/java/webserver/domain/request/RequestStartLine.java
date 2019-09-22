package webserver.domain.request;

import org.slf4j.Logger;
import webserver.domain.common.HttpVersion;

import static org.slf4j.LoggerFactory.getLogger;

public class RequestStartLine {
    private static final Logger LOG = getLogger(Request.class);

    private static final String QUERY_DELIMITER = "\\?";
    private static final int URL_INDEX = 1;
    private static final int PATH_INDEX = 0;
    private static final int METHOD_INDEX = 0;
    private static final int PROTOCOL_INDEX = 2;

    private final HttpMethod httpMethod;
    private final String path;
    private final HttpVersion httpVersion;

    public RequestStartLine(final String[] httpMethodAndPath) {
        final String[] pathAndQuery = httpMethodAndPath[URL_INDEX].split(QUERY_DELIMITER);

        this.httpMethod = HttpMethod.valueOf(httpMethodAndPath[METHOD_INDEX]);
        this.path = pathAndQuery[PATH_INDEX];
        this.httpVersion = HttpVersion.of(httpMethodAndPath[PROTOCOL_INDEX]);

        LOG.debug("RequestStartLine - httpVersion: {}, method: {}, path: {}",
                this.getHttpVersion(), this.getHttpMethod(), this.getPath());
    }

    public String getHttpMethod() {
        return httpMethod.name();
    }

    public String getPath() {
        return path;
    }

    public String getHttpVersion() {
        return httpVersion.getVersion();
    }
}
