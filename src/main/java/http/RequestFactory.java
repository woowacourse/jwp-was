package http;

import org.springframework.util.StringUtils;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class RequestFactory {
    private static final String COLON_DELIMITER = ": ";

    public static Request getRequest(BufferedReader br) throws IOException {
        String line = br.readLine();
        RequestUri requestUri = new RequestUri(line);
        Map<String, String> map = new HashMap<>();
        while (!StringUtils.isEmpty(line = br.readLine())) {
            map.put(line.split(COLON_DELIMITER)[0], line.split(COLON_DELIMITER)[1]);
        }
        RequestHeader requestHeader = new RequestHeader(map);
        return new Request(requestUri, requestHeader);
    }
}
