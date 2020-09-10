package http.request;

import java.util.HashMap;
import java.util.Map;

import utils.IOUtils;

public class HttpUrl {
    
    private static final String PARAM_BUNDLE_DELIMITER = "&";
    private static final String KEY_VALUE_DELIMITER = "=";
    private static final String PATH_PARAM_DELIMITER = "\\?";

    private String path;
    private Map<String, String> params;

    private HttpUrl(String path, Map<String, String> params) {
        this.path = path;
        this.params = params;
    }

    public static HttpUrl from(String url) {
        String decodedUrl = IOUtils.decode(url);
        String[] splittedUrl = splitUrl(decodedUrl);

        if (splittedUrl.length == 2) {
            return new HttpUrl(splittedUrl[0], extractParams(splittedUrl[1]));
        }
        return new HttpUrl(splittedUrl[0], new HashMap<>());
    }

    private static String[] splitUrl(String url) {
        return url.split(PATH_PARAM_DELIMITER);
    }

    private static Map<String, String> extractParams(String paramBundle) {
        Map<String, String> output = new HashMap<>();
        String[] params = paramBundle.split(PARAM_BUNDLE_DELIMITER);
        for (String param : params) {
            String[] keyValues = param.split(KEY_VALUE_DELIMITER);
            output.put(keyValues[0], keyValues[1]);
        }
        return output;
    }

    public String getPath() {
        return path;
    }

    public Map<String, String> getParams() {
        return params;
    }
}
