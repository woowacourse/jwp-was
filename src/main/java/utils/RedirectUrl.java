package utils;

import java.util.Arrays;

public enum RedirectUrl {
    NONE("", "index.html"),
    USER_CREATE("/user/create", "index.html");

    private final String requestUrl;
    private final String redirectUrl;

    RedirectUrl(String requestUrl, String redirectUrl) {
        this.requestUrl = requestUrl;
        this.redirectUrl = redirectUrl;
    }

    public String getRedirectUrl() {
        return redirectUrl;
    }

    public static RedirectUrl findRedirectUrl(String requestUrl) {
        return Arrays.stream(RedirectUrl.values())
            .filter(url -> url.requestUrl.equals(requestUrl))
            .findFirst()
            .orElseGet(() -> NONE);
    }

}
