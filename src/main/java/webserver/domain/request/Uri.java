package webserver.domain.request;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class Uri {
    private final String path;
    private final Map<String, String> parameters;

    public Uri(String path, Map<String, String> parameters) {
        this.path = path;
        this.parameters = parameters;
    }

    public static Uri of(String uri) {
        String[] uriSources =  uri.split("\\?");
        String path = uriSources[0];
        Map<String, String> parameters = new HashMap<>();

        if (uriSources.length > 1) {
            Arrays.stream(uriSources[1].split("&"))
                .forEach(parameter -> parameters.put(parameter.split("=")[0], parameter.split("=")[1]));
        }

        return new Uri(path, parameters);
    }

    public boolean isTemplatesResource() {
        return path.endsWith("html") || path.equals("/favicon.ico");
    }

    public boolean isStaticResource() {
        return !isTemplatesResource();
    }

    public String getPath() {
        return path;
    }

    public Map<String, String> getParameters() {
        return parameters;
    }
}
