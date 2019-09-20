package http.request;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

public class AbstractRequest implements Request {
    protected final RequestMethod requestMethod;
    protected final RequestPath requestPath;
    protected final RequestVersion requestVersion;
    protected final RequestHeader requestHeader;
    protected Map<String, String> parameters;

    public AbstractRequest(List<String> lines, String[] tokens) {
        this.requestMethod = RequestMethod.from(tokens[0]);
        this.requestPath = new RequestPath(tokens[1]);
        this.requestVersion = RequestVersion.from(tokens[2]);
        this.requestHeader = new RequestHeader(lines);
    }

    @Override
    public Map<String, String> getParams() {
        return parameters;
    }

    @Override
    public RequestPath getRequestPath() {
        return requestPath;
    }

    RequestHeader getRequestHeader() {
        return requestHeader;
    }

    @Override
    public RequestMethod getRequestMethod() {
        return requestMethod;
    }

    protected void extractParameter(String[] params) {
        Arrays.stream(params)
                .forEach(param -> {
                    String[] keyValues = param.split("=");
                    parameters.put(keyValues[0], keyValues[1]);
                });
    }
}
