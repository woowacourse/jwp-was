package utils;

public class StringUtils {
    public static final String BLANK = "";
    public static final String WHITE_SPACE = " ";

    public static String[] split(String str, String delimiter) {
        if (str == null || WHITE_SPACE.equals(str)) {
            return null;
        }

        return str.split(delimiter);
    }

    public static boolean isEmpty(String str) {
        return str == null || BLANK.equals(str);
    }

    public static boolean isNotEmpty(String str) {
        return !isEmpty(str);
    }
}
