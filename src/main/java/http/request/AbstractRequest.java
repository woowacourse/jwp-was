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
        this.requestMethod = RequestMethod.from(tokens[0]);
        this.requestPath = new RequestPath(tokens[1]);
        this.requestVersion = RequestVersion.from(tokens[2]);
        this.requestHeader = new RequestHeader(parsedBufferedReader(br));
    }

    @Override
    public List<String> parsedBufferedReader(BufferedReader br) throws IOException {
        List<String> requestLines = new ArrayList<>();
        String line = "Header: start";
        while (!line.equals("")) {
            requestLines.add(line);
            line = br.readLine();
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
