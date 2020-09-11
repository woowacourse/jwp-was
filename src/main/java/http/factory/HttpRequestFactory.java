package http.factory;

import http.HttpRequest;
import http.RequestHeader;
import http.RequestUri;
import org.springframework.util.StringUtils;
import utils.IOUtils;
import utils.ParamUtils;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class HttpRequestFactory {
    private static final String COLON_DELIMITER = ": ";

    public static HttpRequest createRequest(BufferedReader br) throws IOException {
        Map<String, String> headers = new HashMap<>();
        Map<String, String> params = new HashMap<>();
        String line = br.readLine();
        RequestUri requestUri = RequestUriFactory.createRequestUri(line, params);
        line = br.readLine();
        while (!StringUtils.isEmpty(line)) {
            headers.put(line.split(COLON_DELIMITER)[0], line.split(COLON_DELIMITER)[1]);
            line = br.readLine();
        }
        RequestHeader requestHeader = new RequestHeader(headers);

        if (headers.containsKey("Content-Length")) {
            String body = IOUtils.readData(br, Integer.parseInt(headers.get("Content-Length")));
            ParamUtils.putParameter(params, body);
        }
        return new HttpRequest(requestUri, requestHeader, params);
    }
}
