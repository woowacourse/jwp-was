package utils;

public class RequestUtils {

    private static final int URL_INDEX = 1;

    public static String[] separateUrl(final String requestUrl) {
        return requestUrl.split(" ");
    }

    public static boolean isSignIn(final String[] requestUrlArrays) {
        return requestUrlArrays[URL_INDEX].startsWith("/user/create") && "GET".equals(requestUrlArrays[0]);
    }
}
