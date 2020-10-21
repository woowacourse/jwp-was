package web.application.util;

import java.util.Objects;

public class CookieUtils {

    public static boolean checkUserIsNotLogined(String cookies) {
        if (Objects.isNull(cookies) || cookies.isEmpty()) {
            return true;
        }
        return !cookies.contains("logined=true");
    }
}
