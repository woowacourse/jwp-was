package webserver.domain;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class Header {
    private static final String lineSeparator = System.lineSeparator();

    private final Map<String, String> fields;

    public Header(Map<String, String> fields) {
        this.fields = fields;
    }

    public static Header of(BufferedReader bufferedReader) throws IOException {
        // todo : 필드 키를 모두 소문자로 변경해서 저장

        String line = bufferedReader.readLine();

        Map<String, String> headerFields = new HashMap<>();
        while (!line.equals("")) {
            line = bufferedReader.readLine();
            if (line == null) {
                break;
            }
            String[] headerTokens = line.split(": ");
            if (headerTokens.length >= 2) {
                headerFields.put(headerTokens[0], headerTokens[1]);
            }
        }

        return new Header(headerFields);
    }

    public String toValue() {
        StringBuilder sb = new StringBuilder();

        for (Map.Entry<String, String> field : fields.entrySet()) {
            sb.append(String.format("%s: %s%s", field.getKey(), field.getValue(), lineSeparator));
        }

        return sb.toString();
    }

    public String getContentLength() {
        return fields.get("content-length");
    }
}
