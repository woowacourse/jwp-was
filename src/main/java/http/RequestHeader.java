package http;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import utils.IOUtils;

public class RequestHeader {
    private final static String DELIMITER = ": ";

    private Map<String, String> requestHeaders = new HashMap<>();

    public RequestHeader(BufferedReader br) throws IOException {
        String line = br.readLine();

        while (line != null && !"".equals(line)) {
            String[] token = line.split(DELIMITER);
            this.requestHeaders.put(token[0], token[1]);
            line = br.readLine();
        }

        IOUtils.printHeader(requestHeaders);
    }

    public Integer getContentLength() {
        if (requestHeaders.get(Header.CONTENT_TYPE.getName()) == null) {
            return 0;
        }
        return Integer.parseInt(requestHeaders.get(Header.CONTENT_LENGTH.getName()));
    }

    public Map<String, String> getRequestHeaders() {
        return requestHeaders;
    }
}
