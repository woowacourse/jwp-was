package http.factory;

import http.HttpMethod;
import http.RequestUri;

import java.util.HashMap;
import java.util.Map;

public class RequestUriFactory {
    private static final String DELIMITER_METHOD_AND_PATH = " ";
    private static final String DELIMITER_OF_URL_AND_PARAM = "\\?";
    private static final String DELIMITER_OF_PARAM_DATA = "&";
    private static final String DELIMITER_OF_KEY_VALUE = "=";

    public static RequestUri getRequestUri(String line) {
        HttpMethod method = HttpMethod.valueOf(line.split(DELIMITER_METHOD_AND_PATH)[0]);
        String uri = line.split(DELIMITER_METHOD_AND_PATH)[1];
        String[] urlAndParam = uri.split(DELIMITER_OF_URL_AND_PARAM);
        String url = urlAndParam[0];
        Map<String, String> params = new HashMap<>();
        if (urlAndParam.length > 1) {
            makeRequestParam(uri, params);
        }

        return new RequestUri(method, url, params);
    }

    private static Map<String, String> makeRequestParam(String uri, Map<String, String> params) {
        String paramString = uri.split(DELIMITER_OF_URL_AND_PARAM)[1];
        String[] paramData = paramString.split(DELIMITER_OF_PARAM_DATA);
        for (String paramDatum : paramData) {
            params.put(paramDatum.split(DELIMITER_OF_KEY_VALUE)[0], paramDatum.split(DELIMITER_OF_KEY_VALUE)[1]);
        }
        return params;
    }
}
