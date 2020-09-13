package utils;

import java.util.HashMap;

import db.DataBase;
import model.User;

public class RequestUtils {

    private static final int URL_INDEX = 1;
    private static final int SIGN_IN_DATA_KEY_INDEX = 0;
    private static final int SIGN_IN_DATA_VALUE_INDEX = 1;
    private static final int SIGN_IN_INFO_INDEX = 1;

    public static String[] separateUrl(final String requestUrl) {
        return requestUrl.split(" ");
    }

    private static boolean isSignIn(final String[] requestUrlArrays) {
        return requestUrlArrays[URL_INDEX].startsWith("/user/create") && "POST".equals(requestUrlArrays[0]);
    }

    private static HashMap<String, String> parseUserInfo(final String userInfoUrl) {
        HashMap<String, String> signInData = new HashMap<>();
        String[] userInfos = userInfoUrl.split("&");

        for (String userInfo : userInfos) {
            String[] userValues = userInfo.split("=");
            signInData.put(userValues[SIGN_IN_DATA_KEY_INDEX],userValues[SIGN_IN_DATA_VALUE_INDEX]);
        }

        return signInData;
    }

    public static String signIn(final String[] request, String userInfoUrl) {
        String uri = request[URL_INDEX];

        if(RequestUtils.isSignIn(request)) {
            HashMap<String, String> signInInfo = RequestUtils.parseUserInfo(userInfoUrl);
            User user = new User(signInInfo.get("userId"),signInInfo.get("password"),signInInfo.get("name"),signInInfo.get("email"));
            DataBase.addUser(user);
        }

        return uri;
    }
}
