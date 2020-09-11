package utils;

import java.util.Arrays;
import java.util.Map;
import java.util.stream.Collectors;

public class URLUtils {
    public static final String DELIMITER = " ";
    public static final int PATH_INDEX = 1;
    public static final String BASE_URL = "./templates";
    public static final String QUESTION_MARK = "?";
    public static final String PARAMETER_DELIMITER = "&";
    public static final String KEY_VALUE_DELIMITER = "=";
    public static final int ZERO = 0;
    public static final int ONE = 1;

    public static String extractPath(String requestLine) {
        String[] info = requestLine.split(DELIMITER);
        return BASE_URL + info[PATH_INDEX];
    }

    public static Map<String, String> extractParameters(String requestLine) {
        String[] info = requestLine.split(DELIMITER);
        final int questionIndex = info[PATH_INDEX].indexOf(QUESTION_MARK) + ONE;
        final String rawParameters = info[PATH_INDEX].substring(questionIndex);
        final String[] parameters = rawParameters.split(PARAMETER_DELIMITER);

        return Arrays.stream(parameters)
                .map(it -> it.split(KEY_VALUE_DELIMITER))
                .collect(Collectors.toMap(it -> it[ZERO], it -> it[ONE]));
    }
}
