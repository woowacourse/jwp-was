package webserver.request;

import java.util.Map;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import utils.StringUtils;

public class RequestHeader {

    private static final Logger log = LoggerFactory.getLogger(RequestHeader.class);

    private final Map<String, String> headers;

    public RequestHeader(Map<String, String> headers) {
        this.headers = headers;
    }

    public String getHeader(String headerName) {
        String header = headers.get(headerName);
        validate(header);
        return header;
    }

    private void validate(String header) {
        if (StringUtils.isBlank(header)) {
            log.error("{} is Not Exist Header!", header);
            throw new IllegalArgumentException(header + "는 없는 Header 입니다!");
        }
    }

    public void putHeader(String headerKey, String headerValue) {
        headers.put(headerKey, headerValue);
    }

}
