package webserver.httpmessages.request;

import java.util.Arrays;
import java.util.List;

class RequestLine {

    private static final String MESSAGE_INPUT_IS_WRONG = "request line input is unformatted.";

    private Method method;
    private Uri uri;

    RequestLine(String requestLine) {
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

    String getMethod() {
        return method.getMethod();
    }

    String getUri() {
        return uri.getUri();
    }
}
