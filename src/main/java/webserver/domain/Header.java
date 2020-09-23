package webserver.domain;

import java.util.Map;

public class Header {
    private static final String lineSeparator = System.lineSeparator();

    private Map<String, String> fields;

    public Header(Map<String, String> fields) {
        this.fields = fields;
    }

    public String toValue() {
        StringBuilder sb = new StringBuilder();

        for (Map.Entry<String, String> field : fields.entrySet()) {
            sb.append(String.format("%s: %s%s", field.getKey(), field.getValue(), lineSeparator));
        }

        return sb.toString();
    }
}
