package http;

import jdk.internal.joptsimple.internal.Strings;

import java.util.List;

public class HttpBody {
    private static final String NEW_LINE = "\n";

    private RequestParameter requestBody;
    private String body;

    public HttpBody(RequestParameter requestBody, String body) {
        this.requestBody = requestBody;
        this.body = body;
    }

    public HttpBody(List<String> bodyLines) {
        this(null, Strings.join(bodyLines, NEW_LINE));
    }

    public String getParameter(String key) {
        return requestBody.getParameter(key);
    }

    public String getBody() {
        return body;
    }
}