package webserver.request;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class RequestHeader {
    private final Map<String, String> headerAttributes;

    private RequestHeader(Map<String, String> headerAttributes) {
        this.headerAttributes = headerAttributes;
    }

    public static RequestHeader of(BufferedReader br) throws IOException {
        Map<String, String> headerAttributes = new HashMap<>();
        String line = br.readLine();

        while (!"".equals(line) && line != null) {
            String[] attribute = line.split(": ");
            headerAttributes.put(attribute[0], attribute[1]);
            line = br.readLine();
        }
        return new RequestHeader(headerAttributes);
    }

    public String get(String attributeName) {
        if (headerAttributes.containsKey(attributeName)) {
            return headerAttributes.get(attributeName);
        }
        throw new IllegalArgumentException("Not Found Header Attribute");
    }

    public int getContentLength() {
        if (headerAttributes.containsKey("Content-Length")) {
            return Integer.parseInt(headerAttributes.get("Content-Length"));
        }
        return 0;
    }
}
