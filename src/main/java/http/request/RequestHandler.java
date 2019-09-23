package http.request;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import utils.IOUtils;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class RequestHandler {
    private static final Logger logger = LoggerFactory.getLogger(RequestHandler.class);

    private final BufferedReader br;

    public RequestHandler(BufferedReader br) {
        this.br = br;
    }

    public HttpRequest create() throws IOException {
        RequestLine requestLine = createRequestStartLine();
        RequestHeader requestHeader = createRequestHeader();
        RequestBody requestBody = createRequestBody(requestHeader);

        return HttpRequest.of(requestLine, requestHeader, requestBody);
    }

    private RequestBody createRequestBody(RequestHeader requestHeader) throws IOException {
        String body = "";
        if (requestHeader.getHeader("Content-Length") != null) {
            body = IOUtils.readData(br, Integer.parseInt(requestHeader.getHeader("Content-Length")));
        }
        return RequestBody.of(body);
    }

    private RequestHeader createRequestHeader() throws IOException {
        List<String> header = new ArrayList<>();
        String line;
        while (!(line = br.readLine()).equals("")) {
            header.add(line);
        }
        return RequestHeader.of(header);
    }

    private RequestLine createRequestStartLine() throws IOException {
        String startLine = br.readLine();
        return RequestLine.of(startLine);
    }
}
