package webserver.http.request;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public class RequestHeader {
    private static final String BLANK = "";
    private static final String DELIMITER = ":\\s";
    private static final String lineSeparator = System.lineSeparator();

    private final Map<String, String> fields;

    public RequestHeader(Map<String, String> fields) {
        this.fields = fields;
    }

    public static RequestHeader of(BufferedReader bufferedReader) throws IOException {
        Map<String, String> fields = new HashMap<>();

        while(bufferedReader.ready()) {
            String line = bufferedReader.readLine();
            if (line.equals(BLANK)) {
                break;
            }

            String[] headerTokens = line.split(DELIMITER);
            if (headerTokens.length >= 2) {
                fields.put(headerTokens[0].toLowerCase(), headerTokens[1]);
            }
        }


        return new RequestHeader(fields);
    }

    public String toValue() {
        StringBuilder sb = new StringBuilder();

        for (Map.Entry<String, String> field : fields.entrySet()) {
            sb.append(String.format("%s: %s%s", field.getKey(), field.getValue(), lineSeparator));
        }

        return sb.toString();
    }

    public Optional<String> getContentLength() {
        return Optional.ofNullable(fields.get("content-length"));
    }
}
