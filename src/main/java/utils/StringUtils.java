package utils;

import java.util.Objects;

public class StringUtils {

    private static final String EMPTY_STRING = "";

    public static boolean isEmpty(String line) {
        return Objects.isNull(line) || Objects.equals(line, EMPTY_STRING);
    }
}
