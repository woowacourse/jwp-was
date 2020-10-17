package utils;

public enum Directory {
    STATIC,
    TEMPLATES;

    private static final String CLASS_PATH = "./";
    
    public String getDirectory() {
        return CLASS_PATH + this.name().toLowerCase();
    }
}
