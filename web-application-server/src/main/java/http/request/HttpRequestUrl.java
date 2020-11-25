package http.request;

import java.util.HashMap;
import java.util.Map;

public class HttpRequestUrl {

    private static final String QUESTION_MARK = "\\?";
    private static final String AND = "&";
    private static final String EQUAL = "=";

    private final String url;
    private final Map<String, String> parameters;

    public HttpRequestUrl(String url) {
        String[] parsingUrl = parseUrl(url);
        this.url = parsingUrl[0];
        this.parameters = parseParameters(parsingUrl);
    }

    private String[] parseUrl(String url) {
        return url.split(QUESTION_MARK);
    }

    private Map<String, String> parseParameters(String[] parsingUrl) {
        if (parsingUrl.length != 2) {
            return new HashMap<>();
        }

        Map<String, String> cache = new HashMap<>();
        final String[] parameters = parsingUrl[1].split(AND);

        for (String parameter : parameters) {
            String[] data = parameter.split(EQUAL);
            cache.put(data[0], data[1]);
        }

        return cache;
    }

    public String getUrl() {
        return this.url;
    }

    public String getValue(final String name) {
        return this.parameters.get(name);
    }
}
