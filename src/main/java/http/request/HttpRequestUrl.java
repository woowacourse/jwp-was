package http.request;

import java.util.HashMap;
import java.util.Map;

public class HttpRequestUrl {

    private final String url;
    private final Map<String, String> parameters;

    public HttpRequestUrl(String url) {
        String[] parsingUrl = parseUrl(url);
        this.url = parsingUrl[0];
        this.parameters = parseParameters(parsingUrl);
    }

    private String[] parseUrl(String url) {
        return url.split("\\?");
    }

    private Map<String, String> parseParameters(String[] parsingUrl) {
        if (parsingUrl.length == 2) {
            Map<String, String> cache = new HashMap<>();
            final String[] parameters = parsingUrl[1].split("&");

            for (String parameter : parameters) {
                String[] data = parameter.split("=");
                cache.put(data[0], data[1]);
            }

            return cache;
        }
        return new HashMap<>();
    }

    public boolean isEmptyParameters() {
        return this.parameters.isEmpty();
    }

    public String getUrl() {
        return this.url;
    }

    public Map<String, String> getParameters() {
        return this.parameters;
    }

    public String getValue(final String name) {
        return this.parameters.get(name);
    }
}
