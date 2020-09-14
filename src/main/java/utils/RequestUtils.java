package utils;

import java.util.HashMap;

public class RequestUtils {

    private static final int SIGN_IN_DATA_KEY_INDEX = 0;
    private static final int SIGN_IN_DATA_VALUE_INDEX = 1;
    private static final String USER_PARAMETER_SPLIT_STRING = "&";
    private static final String USER_INFO_SPLIT_STRING = "=";
    private static final String URI_SPLIT_STRING = " ";

    public static String[] separateUrl(final String requestUrl) {
        return requestUrl.split(URI_SPLIT_STRING);
    }

    public static HashMap<String, String> parseUserInfo(final String userInfoUrl) {

        HashMap<String, String> signInData = new HashMap<>();
        String[] userInfos = userInfoUrl.split(USER_PARAMETER_SPLIT_STRING);

        for (String userInfo : userInfos) {
            String[] userValues = userInfo.split(USER_INFO_SPLIT_STRING);
            signInData.put(userValues[SIGN_IN_DATA_KEY_INDEX],userValues[SIGN_IN_DATA_VALUE_INDEX]);
        }

        return signInData;
    }
}
