package web;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import utils.HttpRequestUtils;

public class RequestHeader {

    private Map<String, String> headerMap = new HashMap<>();

    public RequestHeader(BufferedReader br) throws IOException {
        String line = br.readLine();
        while (!"".equals(line)) {
            headerMap.put(HttpRequestUtils.extractHeaderKey(line), HttpRequestUtils.extractHeaderValue(line));
            line = br.readLine();
        }
    }

    public String getValue(String key) {
        return this.headerMap.get(key);
    }
}
