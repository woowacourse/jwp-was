package webserver.http.utils;

public class StringUtils {

    private StringUtils() {
    }

    public static boolean isNull(final String text) {
        return text == null;
    }

    public static boolean isEmpty(final String text) {
        return text == null || text.isEmpty();
    }

    public static boolean isNotEmpty(final String text) {
        return !isEmpty(text);
    }
}
