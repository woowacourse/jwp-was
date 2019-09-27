package http.request;

import http.HttpHeader;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import utils.IOUtils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

public class RequestFactory {
    private static final Logger log = LoggerFactory.getLogger(RequestFactory.class);
    private static final String LAST_LINE = "";
    private static final String CONTENT_LENGTH = "Content-Length";

    public static HttpRequest createHttpRequest(InputStream in) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(in));
        HttpRequestStartLine httpRequestStartLine = initializeStart(br);
        HttpHeader httpHeader = initializeHeader(br);
        HttpBody httpBody = initializeBody(br, httpHeader);

        return new HttpRequest(httpRequestStartLine, httpHeader, httpBody);
    }

    private static HttpRequestStartLine initializeStart(BufferedReader br) throws IOException {
        return HttpRequestStartLine.of(br.readLine());
    }

    private static HttpHeader initializeHeader(BufferedReader br) throws IOException {
        List<String> httpRequestHeaderLines = new ArrayList<>();
        HttpHeader httpHeader = new HttpHeader();
        while (true) {
            String line = br.readLine();
            if (line == null || line.equals(LAST_LINE)) {
                break;
            }
            log.debug("{}", line);
            httpRequestHeaderLines.add(line);
        }
        httpHeader.create(httpRequestHeaderLines);
        return httpHeader;
    }

    private static HttpBody initializeBody(BufferedReader br, HttpHeader httpHeader) throws IOException {
        if (!httpHeader.contains(CONTENT_LENGTH)) {
            log.debug("body가 없습니다.");
            return HttpBody.EMPTY_BODY;
        }
        int contentLength = Integer.parseInt(httpHeader.getHeader(CONTENT_LENGTH));
        return HttpBody.of(IOUtils.readData(br, contentLength));
    }

}
