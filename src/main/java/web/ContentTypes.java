package web;

public enum ContentTypes {
    HTML_UTF_8("text/html;charset=utf-8"),
    JAVASCRIPT_UTF_8("application/javascript;charset=UTF-8"),
    CSS_UTF_8("text/css;charset=UTF-8");

    private final String value;

    ContentTypes(final String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
