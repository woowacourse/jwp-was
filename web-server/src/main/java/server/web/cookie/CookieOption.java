package server.web.cookie;

public enum CookieOption {
    EXPIRES("Expires", true),
    MAX_AGE("Max-Age", true),
    DOMAIN("Domain", true),
    PATH("Path", true),
    SECURE("Secure", false),
    HTTP_ONLY("HttpOnly", false),
    SAME_SITE("SameSite", true);

    private final String name;
    private final boolean hasValue;

    CookieOption(String name, boolean hasValue) {
        this.name = name;
        this.hasValue = hasValue;
    }

    public String writeOption(String value) {
        if (this.hasValue) {
            return this.name + "=" + value;
        }
        return this.name;
    }

}
