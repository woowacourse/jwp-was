package web.request;

import exception.InvalidRequestLineException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Objects;

public class RequestLine {
    private static final Logger logger = LoggerFactory.getLogger(RequestLine.class);

    private final MethodType method;
    private final RequestPath requestPath;
    private final String version;

    public RequestLine(String requestLine) {
        validateLine(requestLine);
        logger.debug(requestLine);

        String[] tokens = requestLine.split(" ");
        String methodName = tokens[0].trim();
        this.method = MethodType.createMethodByName(methodName);
        this.requestPath = new RequestPath(tokens[1].trim());
        this.version = tokens[2].trim();
    }

    private void validateLine(String requestLine) {
        if (Objects.isNull(requestLine) || requestLine.isEmpty()) {
            throw new InvalidRequestLineException();
        }
    }

    public MethodType getMethod() {
        return method;
    }

    public RequestPath getRequestPath() {
        return requestPath;
    }

    public String getVersion() {
        return version;
    }
}
