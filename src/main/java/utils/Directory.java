package utils;

public enum Directory {
    STATIC,
    TEMPLATES;

    public String getDirectory() {
        return "./" + this.name().toLowerCase();
    }
}
