package http.request;

import java.util.List;

public class AbstractRequest implements Request {
    protected final RequestMethod requestMethod;
    protected final RequestPath requestPath;
    protected final RequestVersion requestVersion;
    protected final RequestHeader requestHeader;

    public AbstractRequest(List<String> lines, String[] tokens) {
        this.requestMethod = RequestMethod.from(tokens[0]);
        this.requestPath = new RequestPath(tokens[1]);
        this.requestVersion = RequestVersion.from(tokens[2]);
        this.requestHeader = new RequestHeader(lines);
    }

    @Override
    public RequestMethod getRequestMethod() {
        return requestMethod;
    }

    @Override
    public RequestPath getRequestPath() {
        return requestPath;
    }

    @Override
    public RequestHeader getRequestHeader() {
        return requestHeader;
    }
}
