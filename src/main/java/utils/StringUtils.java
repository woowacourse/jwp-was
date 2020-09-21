package utils;

import java.util.Objects;
import java.util.function.Supplier;

public class StringUtils {

    private StringUtils() {
    }

    public static void validateNonNullAndNotEmpty(Supplier<RuntimeException> exceptionSupplier, String... value) {
        for (String v : value) {
            if (isNullOrEmpty(v)) {
                throw exceptionSupplier.get();
            }
        }
    }

    private static boolean isNullOrEmpty(String v) {
        return Objects.isNull(v) || v.isEmpty();
    }

    public static boolean isNotNumber(String value) {
        if (isNullOrEmpty(value)) {
            return true;
        }

        for (int i = 0; i < value.length(); i++) {
            char c = value.charAt(i);
            if (!('0' <= c && c <= '9')) {
                return true;
            }
        }

        return false;
    }
}
