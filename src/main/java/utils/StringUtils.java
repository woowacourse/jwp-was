package utils;

import java.util.Optional;

public class StringUtils {

    private StringUtils() {
    }

    public static void validateNonNullAndNotEmpty(String value) {
        Optional.ofNullable(value)
                .filter(v -> !v.isEmpty())
                .orElseThrow(IllegalArgumentException::new);
    }
}
