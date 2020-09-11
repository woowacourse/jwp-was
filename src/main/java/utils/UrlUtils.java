package utils;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class UrlUtils {

    private static final int FILE_INDEX = 0;
    private static final int PARAMS_INDEX = 1;
    private static final int DEFAULT_PARAMS_ACCESS_INDEX = 2;
    private static final int DEFAULT_PARAM_ACCESS_INDEX = 2;
    private static final String ROOT_PATH = "/";
    private static final String PARAM_DELIMITER = "&";
    private static final String PARAMS_DELIMITER = "\\?";
    private static final String PARAM_KEY_VALUE_DELIMITER = "=";
    private static final String BLANK_VALUE = "";

    public static String extractFilePath(String resourcePath) {
        String filePath = resourcePath.split(PARAMS_DELIMITER)[FILE_INDEX];

        if (isBlank(filePath)) {
            return ROOT_PATH;
        }
        return filePath;
    }

    public static Map<String, String> extractRequestParamFromUrl(String resourcePath) {
        if (isBlank(resourcePath)) {
            return Collections.emptyMap();
        }
        String[] resourcePathSegment = resourcePath.split(PARAMS_DELIMITER);

        if (isNotAccessibleParams(resourcePathSegment)) {
            return Collections.emptyMap();
        }
        String params = resourcePathSegment[PARAMS_INDEX];

        return extractRequestParam(params);
    }

    public static Map<String, String> extractRequestParam(String params) {
        Map<String, String> requestParams = new HashMap<>();

        String[] paramsSegments = params.split(PARAM_DELIMITER);
        for (String paramsSegment : paramsSegments) {
            String[] paramSegment = paramsSegment.split(PARAM_KEY_VALUE_DELIMITER);

            if (isNotAccessibleParam(paramSegment)) {
                continue;
            }
            String paramKey = paramSegment[0];
            String paramValue = paramSegment[1];
            requestParams.put(paramKey, paramValue);
        }
        return requestParams;
    }

    private static boolean isBlank(String input) {
        return Objects.isNull(input) || BLANK_VALUE.equals(input.trim());
    }

    private static boolean isNotAccessibleParam(String[] paramSegment) {
        return paramSegment.length != DEFAULT_PARAM_ACCESS_INDEX;
    }

    private static boolean isNotAccessibleParams(String[] resourcePathSegment) {
        return resourcePathSegment.length != DEFAULT_PARAMS_ACCESS_INDEX;
    }
}
