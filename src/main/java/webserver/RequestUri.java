package webserver;

import exception.MissingRequestParameterException;
import java.util.Objects;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import utils.UrlUtils;

public class RequestUri {

    private static final int PARAMS_DELIMITER_LENGTH = 2;
    private static final int REQUEST_URI_DELIMITER_LIMIT = 2;
    private static final int REQUEST_URI_DELIMITER_COUNT = 1;
    private static final String REQUEST_URI_DELIMITER = "\\?";
    private static final Logger log = LoggerFactory.getLogger(RequestUri.class);

    private final RequestPath path;
    private RequestParameters parameters;

    public RequestUri(String requestUri) {
        validate(requestUri);
        String[] requestUriSegment = requestUri.trim().split(REQUEST_URI_DELIMITER,
            REQUEST_URI_DELIMITER_LIMIT);
        this.path = new RequestPath(requestUriSegment[0]);
        if (isAccessibleParameters(requestUriSegment)) {
            this.parameters = new RequestParameters(requestUriSegment[1]);
        }
    }

    private boolean isAccessibleParameters(String[] requestUriSegment) {
        return requestUriSegment.length == PARAMS_DELIMITER_LENGTH;
    }

    private boolean isAvailableDelimiter(String requestUri) {
        return countDelimiter(requestUri, REQUEST_URI_DELIMITER) == REQUEST_URI_DELIMITER_COUNT;
    }

    private int countDelimiter(String requestUri, String character) {
        return requestUri.length() - requestUri.replaceAll(character, "").length();
    }

    private void validate(String requestUri) {
        if (UrlUtils.isBlank(requestUri)) {
            log.error("Request Uri Blank or Null Error! : requestUri is {} ", requestUri);
            throw new IllegalArgumentException("Request Uri : " + requestUri + "가 Null 또는 공백입니다!");
        }
        if (requestUri.endsWith("?") && isAvailableDelimiter(requestUri)) {
            log.error("Request Uri Not Exist Parameter Error! : requestUri is {} ", requestUri);
            throw new MissingRequestParameterException("Request Uri : " + requestUri + "에 Parameter가 없습니다! ");
        }
    }

    public String getPath() {
        return path.getRequestPath();
    }

    public String getParameter(String key) {
        validate(parameters);
        return parameters.getValue(key);
    }

    private void validate(RequestParameters parameters) {
        if (Objects.isNull(parameters)) {
            log.error("Parameters Null Error! : Parameters is null ");
            throw new MissingRequestParameterException("Request Uri의 Parameters가 Null입니다!");
        }
    }
}
