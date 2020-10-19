package utils;

import java.util.Objects;

public class StringUtils {

    public static final String SPACE = " ";
    private static final String BLANK_VALUE = "";

    public static boolean isBlank(String input) {
        return Objects.isNull(input) || BLANK_VALUE.equals(input.trim());
    }
}
