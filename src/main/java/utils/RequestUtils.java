package utils;

import java.util.HashMap;

public class RequestUtils {

    private static final int URL_INDEX = 1;
    private static final int SIGN_IN_DATA_KEY_INDEX = 0;
    private static final int SIGN_IN_DATA_VALUE_INDEX = 1;

    public static String[] separateUrl(final String requestUrl) {
        return requestUrl.split(" ");
    }

    public static boolean isSignIn(final String[] requestUrlArrays) {
        return requestUrlArrays[URL_INDEX].startsWith("/user/create") && "GET".equals(requestUrlArrays[0]);
    }

    public static HashMap<String, String> parseUserInfo(final String userInfoUrl) {
        HashMap<String, String> signInData = new HashMap<>();
        String[] userInfos = userInfoUrl.split("&");

        for (String userInfo : userInfos) {
            String[] userValues = userInfo.split("=");
            signInData.put(userValues[SIGN_IN_DATA_KEY_INDEX],userValues[SIGN_IN_DATA_VALUE_INDEX]);
        }
        return signInData;
    }
}
