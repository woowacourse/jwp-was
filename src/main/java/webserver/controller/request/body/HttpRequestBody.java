package webserver.controller.request.body;

import org.checkerframework.checker.units.qual.K;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public class HttpRequestBody {
    private final HashMap <String, String> bodyDataSet;

    public HttpRequestBody(String requestParams) throws UnsupportedEncodingException {
        bodyDataSet = new HashMap<>();
        requestParams = URLDecoder.decode(requestParams, "UTF-8");
        String[] splitedParams = requestParams.split("&");

        for(String splitedParam : splitedParams) {
            String[] queryParam = splitedParam.split("=");
            bodyDataSet.put(queryParam[0], queryParam[1]);
        }
    }

    public Map<String, String> getBodyDataSet() {
        return Collections.unmodifiableMap(bodyDataSet);
    }
}
