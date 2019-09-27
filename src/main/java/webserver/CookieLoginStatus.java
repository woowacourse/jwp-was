package webserver;

public enum CookieLoginStatus {
    TRUE("true"),
    False("false");

    private final String text;

    CookieLoginStatus(String text) {
        this.text = text;
    }

    public static boolean match(CookieLoginStatus loginStatus, String text) {
        return loginStatus.text.equals(text);
    }

    public String getText() {
        return text;
    }
}
