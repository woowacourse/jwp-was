package utils;

public enum Extension {
    JS("js"),
    CSS("css"),
    HTML("html");

    private final String title;

    Extension(String title) {
        this.title = title;
    }

    public static String getContentType(String extension) {
        if (JS.title.equals(extension)) {
            return "application/javascript;charset=utf-8";
        }
        if (CSS.title.equals(extension) || HTML.title.equals(extension)) {
            return String.format("text/%s;charset=utf-8", extension);
        }
        return null;
    }
}
