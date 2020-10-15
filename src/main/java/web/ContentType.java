package web;

public enum ContentType {
    HTML_UTF_8("text/html;charset=utf-8"),
    JAVASCRIPT_UTF_8("application/javascript;charset=UTF-8"),
    CSS_UTF_8("text/css;charset=UTF-8");

    private final String value;

    ContentType(final String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
