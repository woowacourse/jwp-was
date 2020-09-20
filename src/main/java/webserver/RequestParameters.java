package webserver;

import exception.MissingRequestParameterException;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class RequestParameters {

    private static final int DEFAULT_PARAM_ACCESS_INDEX = 2;
    private static final int PARAM_KEY_VALUE_DELIMITER_LIMIT = 2;
    private static final String PARAM_DELIMITER = "&";
    private static final String PARAM_KEY_VALUE_DELIMITER = "=";
    private static final Logger log = LoggerFactory.getLogger(RequestUri.class);

    private final Map<String, String> parameters;

    public RequestParameters(String parameters) {
        Map<String, String> requestParams = new HashMap<>();

        String[] paramsSegments = parameters.split(PARAM_DELIMITER);
        for (String paramsSegment : paramsSegments) {
            String[] paramSegment = paramsSegment.split(PARAM_KEY_VALUE_DELIMITER, PARAM_KEY_VALUE_DELIMITER_LIMIT);
            String paramValue = "";

            if (isNotAccessibleParam(paramSegment)) {
                continue;
            }
            String paramKey = paramSegment[0];

            if (paramSegment.length == 2) {
                paramValue = paramSegment[1];
            }

            if (requestParams.containsKey(paramKey)) {
                String prevParamValue = requestParams.get(paramKey);
                requestParams.put(paramKey, prevParamValue + "," + paramSegment[1]);
                continue;
            }
            requestParams.put(paramKey, paramValue);
        }
        this.parameters = requestParams;
    }

    private static boolean isNotAccessibleParam(String[] paramSegment) {
        return paramSegment.length > DEFAULT_PARAM_ACCESS_INDEX;
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
