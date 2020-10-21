package http.request;

import java.io.BufferedReader;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.StringUtils;

import exceptions.InvalidHttpRequestException;
import http.HttpMethod;
import http.HttpVersion;
import utils.IOUtils;

public class RequestLine {

    private static final Logger logger = LoggerFactory.getLogger(RequestLine.class);
    private static final String REQUEST_LINE_DELIMITER = " ";

    private final HttpMethod method;
    private final RequestPath path;
    private final HttpVersion version;

    public RequestLine(BufferedReader bufferedReader) {
        String requestLine = IOUtils.readRequestLine(bufferedReader);
        logger.info("{}", requestLine);
        if (StringUtils.isEmpty(requestLine)) {
            logger.info("올바른 HTTP 요청이 아닙니다. RequestLine을 확인해주세요");
            throw new InvalidHttpRequestException();
        }
        String[] splitRequestLine = requestLine.split(REQUEST_LINE_DELIMITER);
        this.method = HttpMethod.from(splitRequestLine[0]);
        this.path = RequestPath.from(splitRequestLine[1]);
        this.version = HttpVersion.from(splitRequestLine[2]);
    }

    public HttpMethod getMethod() {
        return method;
    }

    public String getPath() {
        return path.getPath();
    }

    public String getParameters(String key) {
        return path.getParameters(key);
    }

    public Map<String, String> getParam() {
        return path.getParameters();
    }

    public HttpVersion getVersion() {
        return version;
    }
}
