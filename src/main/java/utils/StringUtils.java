package utils;

public class StringUtils {

    private static final String REQUEST_LINE_DELIMITER = " ";
    private static final int METHOD_INDEX = 0;
    private static final int LOCATION_INDEX = 1;
    private static final int VALID_REQUEST_SIZE = 3;
    private static final String KEY_VALUE_LINK_LETTER = "=";
    private static final String PARAMETER_DELIMITER = "&";
    private static final int VALUE_RIGHT_SIDE_INDEX = 1;
    private static final int VALUE_INDEX = 0;
    private static final String EXTENSION_DELIMITER = "\\.";
    private static final int NO_EXTENSION_SIZE = 1;
    private static final int SIZE_CORRECTION_NUMBER = 1;
    private static final String EXTENSION_LETTER = ".";

    public static String extractRequestMethod(String line) {
        String[] requestLine = line.split(REQUEST_LINE_DELIMITER);
        if (requestLine.length != VALID_REQUEST_SIZE) {
            throw new IllegalArgumentException("Invalid Request");
        }
        return requestLine[METHOD_INDEX];
    }

    public static String extractRequestLocation(String line) {
        String[] requestLine = line.split(REQUEST_LINE_DELIMITER);
        if (requestLine.length != VALID_REQUEST_SIZE) {
            throw new IllegalArgumentException("Invalid Request");
        }
        return requestLine[LOCATION_INDEX];
    }

    public static String extractParameterValue(String params, String key) {
        return params
            .split(key + KEY_VALUE_LINK_LETTER)[VALUE_RIGHT_SIDE_INDEX]
            .split(PARAMETER_DELIMITER)[VALUE_INDEX];
    }

    public static String extractExtension(String line) {
        String location = line.split(REQUEST_LINE_DELIMITER)[LOCATION_INDEX];
        String[] sections = location.split(EXTENSION_DELIMITER);

        if (sections.length == NO_EXTENSION_SIZE) {
            return null;
        }
        return EXTENSION_LETTER + sections[sections.length - SIZE_CORRECTION_NUMBER];
    }
}
