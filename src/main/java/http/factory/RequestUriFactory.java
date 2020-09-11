package http.factory;

import http.HttpMethod;
import http.RequestUri;
import utils.ParamUtils;

import java.util.Map;

public class RequestUriFactory {
    private static final String DELIMITER_METHOD_AND_PATH = " ";
    private static final String DELIMITER_OF_URL_AND_PARAM = "\\?";

    public static RequestUri createRequestUri(String line, Map<String, String> params) {
        HttpMethod method = HttpMethod.valueOf(line.split(DELIMITER_METHOD_AND_PATH)[0]);
        String uri = line.split(DELIMITER_METHOD_AND_PATH)[1];
        String[] urlAndParam = uri.split(DELIMITER_OF_URL_AND_PARAM);
        String url = urlAndParam[0];
        if (urlAndParam.length > 1) {
            ParamUtils.putParameter(params, urlAndParam[1]);
        }
        return new RequestUri(method, url);
    }
}
