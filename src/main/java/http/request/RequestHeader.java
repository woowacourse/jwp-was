package http.request;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class RequestHeader {
    private static final String HEADER_DELIMITER = ":\\s";
    private static final String CRLF = "\r\n";

    private Map<String, String> requestHeader;

    private RequestHeader(Map<String, String> requestHeader) {
        this.requestHeader = requestHeader;
    }

    public static RequestHeader of(BufferedReader br) throws IOException {
        RequestHeader requestHeader = new RequestHeader(new HashMap<>());
        String header;
        while (!(header = br.readLine()).equals("")) {
            requestHeader.put(header);
        }

        return requestHeader;
    }

    private void put(String headerLine) {
        String[] tokens = headerLine.split(HEADER_DELIMITER);
        requestHeader.put(tokens[0], tokens[1]);
    }

    public String getHeader(String key) {
        return requestHeader.get(key);
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        for (String key : requestHeader.keySet()) {
            sb.append(key + HEADER_DELIMITER +  requestHeader.get(key) + CRLF);
        }
        return sb.toString();
    }
}
