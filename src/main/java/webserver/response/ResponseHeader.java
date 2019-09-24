package webserver.response;

import java.util.HashMap;
import java.util.Map;

public class ResponseHeader {
    private Map<String, String> attributes;

    public ResponseHeader() {
        this.attributes = new HashMap<>();
    }

    public String response(String attribute) {
        return attribute + ": " + "text/html;" + " " + "charset=utf-8\r\n";
    }
}
