package webserver.controller.request.body;


import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public class HttpRequestBody {
    private static final String KEY_VALUE_DELEMETER = "=";
    private static final String FIELD_SEPERATE_DELEMETER = "&";
    private final HashMap<String, String> bodyDataSet;

    public HttpRequestBody(String requestParams) {
        bodyDataSet = new HashMap<>();
        requestParams = URLDecoder.decode(requestParams, StandardCharsets.UTF_8);
        String[] splitedParams = requestParams.split(FIELD_SEPERATE_DELEMETER);

        mappingRequestParams(splitedParams);
    }

    private void mappingRequestParams(String[] splitedParams) {
        for (String splitedParam : splitedParams) {
            String[] queryParam = splitedParam.split(KEY_VALUE_DELEMETER);
            bodyDataSet.put(queryParam[0], queryParam[1]);
        }
    }

    public Map<String, String> getBodyDataSet() {
        return Collections.unmodifiableMap(bodyDataSet);
    }
}
