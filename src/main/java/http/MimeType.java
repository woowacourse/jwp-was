package http;

public enum MimeType {
    CSS("text/css;charset=utf-8"),
    JS("text/javascript;charset=UTF-8"),
    HTML("text/html;charset=utf-8");

    private final String type;

    MimeType(final String type) {
        this.type = type;
    }

    public static String getType(String type) {
        for (final MimeType mimeType : MimeType.values()) {
            if (mimeType.type.contains(type)) {
                return mimeType.type;
            }
        }

        throw new IllegalArgumentException("Invalid MimeType");
    }
}
