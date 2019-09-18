package utils;

import java.util.HashMap;
import java.util.Map;

public class ExtractInformationUtils {
    public static Map<String, String> extractInformation(String queryString) {
        Map<String, String> userInfo = new HashMap<>();
        String[] queryParams = queryString.substring(queryString.indexOf("?") + 1).split("&");

        for (String queryParam : queryParams) {
            String[] separatedParam = queryParam.split("=");
            System.out.println(queryParam);
            userInfo.put(separatedParam[0], separatedParam[1]);
        }

        return userInfo;
    }
}
