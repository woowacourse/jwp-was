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

    private RequestLine createRequestStartLine() throws IOException {
        String startLine = br.readLine();
        return RequestLine.of(startLine);
    }

    private RequestHeader createRequestHeader() throws IOException {
        List<String> header = new ArrayList<>();
        String line;
        while (!("".equals(line = br.readLine()))) {
            header.add(line);
            logger.debug("requestHeader: {}", line);
        }

        return RequestHeader.of(header);
    }

    private RequestBody createRequestBody(RequestHeader requestHeader) throws IOException {
        if (requestHeader.getHeader(HttpRequest.CONTENT_LENGTH_NAME) != null) {
            byte[] body = IOUtils.readData(br, Integer.parseInt(requestHeader.getHeader(HttpRequest.CONTENT_LENGTH_NAME))).getBytes();
            return RequestBody.of(body);
        }

        return RequestBody.of("".getBytes());
    }
}
