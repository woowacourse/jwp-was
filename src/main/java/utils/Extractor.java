package utils;

import http.SessionContainer;

import java.util.Arrays;
import java.util.Optional;

public class Extractor {
    private static final String DELIMITER_BLANK = " ";
    private static final String DELIMITER_EQUAL = "=";
    private static final String DELIMITER_OF_PATH_AND_PARAMS = "\\?";

    public static String methodFromRequestLine(String line) {
        return line.split(DELIMITER_BLANK)[0];
    }

    public static String requestUriFromRequestLine(String line) {
        return line.split(DELIMITER_BLANK)[1];
    }

    public static String pathFromRequestLine(String line) {
        String requestUri = requestUriFromRequestLine(line);
        return requestUri.split(DELIMITER_OF_PATH_AND_PARAMS)[0];
    }

    public static String paramFromRequestLine(String line) {
        String requestUri = requestUriFromRequestLine(line);
        String[] pathAndParams = requestUri.split(DELIMITER_OF_PATH_AND_PARAMS);
        if (isNoneParams(pathAndParams)) {
            return "";
        }
        return pathAndParams[1];
    }

    private static boolean isNoneParams(String[] pathAndParam) {
        return pathAndParam.length == 1;
    }

    public static Optional<String> sessionIdFrom(String cookieLine) {
        String[] cookies = cookieLine.split(DELIMITER_BLANK);
        return Arrays.stream(cookies)
                .filter(Extractor::isSessionId)
                .map(line -> line.split(DELIMITER_EQUAL)[1])
                .findAny();
    }

    private static boolean isSessionId(String cookie) {
        return cookie.split(DELIMITER_EQUAL)[0].equals(SessionContainer.SESSION_KEY_FOR_COOKIE);
    }
}
