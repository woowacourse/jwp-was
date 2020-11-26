package utils;

import java.util.HashMap;
import java.util.Map;

public class UriUtils {

    private static final int PARAM_KEY_VALUE_DELIMITER_LIMIT = 2;
    private static final String PARAM_DELIMITER = "&";
    private static final String PARAM_KEY_VALUE_DELIMITER = "=";

    public static Map<String, String> extractRequestParam(String params) {
        Map<String, String> requestParams = new HashMap<>();

        String[] paramsSegments = params.split(PARAM_DELIMITER);
        for (String paramsSegment : paramsSegments) {
            String[] paramSegment = paramsSegment
                .split(PARAM_KEY_VALUE_DELIMITER, PARAM_KEY_VALUE_DELIMITER_LIMIT);
            String paramValue = "";

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
        return requestParams;
    }
}
