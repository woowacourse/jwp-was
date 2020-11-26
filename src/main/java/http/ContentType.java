package http;

public enum ContentType {
    APPLICATION_X_WWW_FORM_URLENCODED("application/x-www-form-urlencoded");

    private final String name;

    ContentType(String name) {
        this.name = name;
    }

    public boolean match(String name) {
        return this.name.equals(name);
    }
}
