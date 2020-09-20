package webserver;

import exception.MissingRequestParameterException;
import java.util.Map;
import java.util.Objects;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import utils.UrlUtils;

public class RequestParameters {

    private static final Logger log = LoggerFactory.getLogger(RequestUri.class);

    private final Map<String, String> parameters;

    public RequestParameters(String parameters) {
        this.parameters = UrlUtils.extractRequestParam(parameters);
    }

    public String getValue(String key) {
        String value = parameters.get(key);
        if (Objects.isNull(value)) {
            log.error("Parameter is Null Error! : Parameter is null ");
            throw new MissingRequestParameterException("Request Parameter의 key값이 존재하지 않습니다!");
        }
        return value;
    }
}
