package webserver;

public class TemplateModel {
    private final String key;
    private final Object value;

    public TemplateModel(String key, Object value) {
        this.key = key;
        this.value = value;
    }

    public String getKey() {
        return key;
    }

    public Object getValue() {
        return value;
    }
}
