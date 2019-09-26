package test;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class BaseTest {

    public Map<String, String> makePostHeader(String ... values) {
        Map<String, String> header = new HashMap<>();
        List<String> keys = Arrays.asList("Request-Line:", "Content-Length:", "Content-Type:", "Query-Parameters:");
        for (int i = 0; i < values.length; i++) {
            header.put(keys.get(i), values[i]);
        }
        return header;
    }

    public Map<String, String> makeGetHeader(String ... values) {
        Map<String, String> header = new HashMap<>();
        List<String> keys = Arrays.asList("Request-Line:");
        for (int i = 0; i < values.length; i++) {
            header.put(keys.get(i), values[i]);
        }
        return header;
    }


}
