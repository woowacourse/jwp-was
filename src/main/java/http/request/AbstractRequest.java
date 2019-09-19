package http.request;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class AbstractRequest implements Request {
    private final RequestMethod requestMethod;
    private final RequestPath requestPath;
    private final RequestVersion requestVersion;
    private final RequestHeader requestHeader;

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

}
