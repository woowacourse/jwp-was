package http.request;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class AbstractRequest implements Request {
    protected final RequestMethod requestMethod;
    protected final RequestPath requestPath;
    protected final RequestVersion requestVersion;
    protected final RequestHeader requestHeader;
    protected Map<String, String> parameters;


    public AbstractRequest(BufferedReader br, String[] tokens) throws IOException {
        String line = tokens[0] + tokens[1] + tokens[2];
        this.requestMethod = RequestMethod.from(tokens[0]);
        this.requestPath = new RequestPath(tokens[1]);
        this.requestVersion = RequestVersion.from(tokens[2]);
        this.requestHeader = new RequestHeader(parsedBufferedReader(br, line));
    }

    @Override
    public List<String> parsedBufferedReader(BufferedReader br, String line) throws IOException {
        List<String> requestLines = new ArrayList<>();

        while (!line.equals("")) {
            line = br.readLine();
            requestLines.add(line);
        }

        return requestLines;
    }

    @Override
    public Map<String, String> getParams() {
        return parameters;
    }

    public RequestPath getRequestPath() {
        return requestPath;
    }
}
