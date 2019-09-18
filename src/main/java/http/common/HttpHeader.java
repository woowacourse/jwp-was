package http.common;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class HttpHeader {
    private static final String HEADER_DELIMITER = ": ";

    private Map<String, String> httpHeader;

    private HttpHeader(Map<String, String> httpHeader) {
        this.httpHeader = httpHeader;
    }

    public static HttpHeader of(BufferedReader br) throws IOException {
        HttpHeader httpHeader = new HttpHeader(new HashMap<>());
        String header;
        while (!(header = br.readLine()).equals("")) {
            httpHeader.putHeader(header);
        }

        return httpHeader;
    }

    public String getHeader(String headerKey) {
        return httpHeader.get(headerKey);
    }

    private void putHeader(String headerLine) {
        String[] tokens = headerLine.split(HEADER_DELIMITER);
        httpHeader.put(tokens[0], tokens[1]);
    }
}
