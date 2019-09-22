package utils;

import java.util.HashMap;
import java.util.Map;

public class ExtractInformationUtils {
    private static final String EQUAL = "=";
    private static final String SEPARATOR = "&";

    public static Map<String, String> extractInformation(String body) {
        Map<String, String> userInfo = new HashMap<>();
        String[] params = body.split(SEPARATOR);

        for (String param : params) {
            String[] separatedParam = param.split(EQUAL);
            userInfo.put(separatedParam[0], separatedParam[1]);
        }

        return userInfo;
    }
}
