package utils;

import java.util.Objects;

public class StringUtils {
    private static final String BLANK_VALUE = "";
    public static final String SPACE = " ";

    public static boolean isBlank(String input) {
        return Objects.isNull(input) || BLANK_VALUE.equals(input.trim());
    }
}
