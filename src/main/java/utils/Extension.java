package utils;

public enum Extension {
    JS("js"),
    CSS("css"),
    HTML("html");

    private final String title;

    Extension(String title) {
        this.title = title;
    }

    public static boolean isJS(String extension) {
        return extension.equals(JS.title);
    }

    public static boolean isCSS(String extension) {
        return extension.equals(CSS.title);
    }

    public static boolean isHTML(String extension) {
        return extension.equals(HTML.title);
    }
}
