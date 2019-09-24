package webserver;

public class ResponseBody {
    private String key;
    private Object value;

    public ResponseBody(String key, Object value) {
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
