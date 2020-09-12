package request;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

public class RequestLine {

    private static final String MESSAGE_INPUT_IS_WRONG = "request line input is unformatted.";

    private Method method;
    private Uri uri;

    public RequestLine(String requestLine) {
        List<String> split = Arrays.asList(requestLine.split(" "));
        if (split.size() != 3) {
            throw new IllegalArgumentException(MESSAGE_INPUT_IS_WRONG);
        }
        try {
            method = Method.from(split.get(0));
            uri = new Uri(split.get(1));
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException("request line input is unformatted.");
        }
    }

    boolean isUriUsingQueryString() {
        return uri.isUsingQueryString();
    }

    Map<String, String> getQueryDataFromUri() {
        return uri.getDataFromGetMethodUri();
    }

    boolean isUriPath(String uriPath) {
        return uri.isPath(uriPath);
    }

    public String getMethod() {
        return method.getMethod();
    }

    String getUriPath() {
        return uri.getPath();
    }

    boolean isMethod(Method method) {
        return this.method == method;
    }
}
