package utils;

public class Extractor {
    private static final String DELIMITER_METHOD_AND_PATH = " ";
    private static final String DELIMITER_OF_PATH_AND_PARAMS = "\\?";

    public static String methodFromRequestLine(String line) {
        return line.split(DELIMITER_METHOD_AND_PATH)[0];
    }

    public static String requestUriFromRequestLine(String line) {
        return line.split(DELIMITER_METHOD_AND_PATH)[1];
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
}
