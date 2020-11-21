package webserver.http.response;

import java.util.Map;

public class ResponseHeader {
    private static final String lineSeparator = System.lineSeparator();

    private final Map<String, String> fields;

    public ResponseHeader(Map<String, String> fields) {
        this.fields = fields;
    }

    public String getCookie() {
        return fields.get("set-Cookie");
    }

    public String toValue() {
        StringBuilder sb = new StringBuilder();

        for (Map.Entry<String, String> field : fields.entrySet()) {
            sb.append(String.format("%s: %s%s", field.getKey(), field.getValue(), lineSeparator));
        }

        return sb.toString();
    }
}
