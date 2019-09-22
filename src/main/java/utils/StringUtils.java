package utils;

public class StringUtils {

    private StringUtils() {
    }

    public static boolean isEmpty(final String text) {
        return text == null || text.isEmpty();
    }

    public static boolean isNotEmpty(final String text) {
        return !isEmpty(text);
    }
}
