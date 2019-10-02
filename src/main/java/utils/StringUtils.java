package utils;

public class StringUtils {
    public static final String BLANK = "";

    public static String[] split(String str, String delimiter) {
        return isNull(str) ? new String[0] : str.split(delimiter);
    }

    public static boolean isEmpty(String str) {
        return isNull(str) || BLANK.equals(str);
    }

    public static boolean isNotEmpty(String str) {
        return !isEmpty(str);
    }

    public static boolean isNull(String str) {
        return str == null;
    }

    public static boolean isNotBlank(String[] url) {
        return url != null && url.length > 0;
    }
}
