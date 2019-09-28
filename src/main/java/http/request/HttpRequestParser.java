package http.request;

import http.HttpBody;
import http.HttpHeader;
import http.HttpVersion;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import utils.IOUtils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import static http.HttpString.EMPTY;

public class HttpRequestParser {
    private static final Logger log = LoggerFactory.getLogger(HttpRequestParser.class);

    private static final String START_LINE_DELIMITER = " ";

    public static HttpRequest parse(InputStream in) {
        HttpRequest.HttpRequestBuilder builder = new HttpRequest.HttpRequestBuilder();

        try {
            BufferedReader br = new BufferedReader(new InputStreamReader(in));

            HttpRequestLine httpRequestLine = parseRequestLine(br);
            HttpHeader httpHeader = parseHeader(br);
            HttpBody httpBody = parserBody(br, httpHeader);

            printLogRequestMessage(httpRequestLine, httpHeader, httpBody);

            return builder
                    .requestLine(httpRequestLine)
                    .header(httpHeader)
                    .body(httpBody)
                    .build();
        } catch (IOException e) {
            log.error(e.getMessage());
            throw new IllegalArgumentException();
        }
    }

    private static HttpRequestLine parseRequestLine(BufferedReader br) throws IOException {
        String startLine = br.readLine();
        String[] startLineTokens = startLine.split(START_LINE_DELIMITER);
        HttpRequestMethod method = HttpRequestMethod.of(startLineTokens[0]);
        HttpRequestUri uri = new HttpRequestUri(startLineTokens[1]);
        HttpVersion version = HttpVersion.of(startLineTokens[2]);
        return new HttpRequestLine(method, uri, version);
    }

    private static HttpHeader parseHeader(BufferedReader br) throws IOException {
        List<String> headerLines = new ArrayList<>();
        String header = br.readLine();
        while (!EMPTY.equals(header)) {
            if (header == null) {
                break;
            }

            headerLines.add(header);
            header = br.readLine();
        }

        return new HttpHeader(headerLines);
    }

    private static HttpBody parserBody(BufferedReader br, HttpHeader httpHeader) throws IOException {
        if (httpHeader.getContentLength() <= 0) {
            return new HttpBody(EMPTY);
        }

        return new HttpBody(IOUtils.readData(br, httpHeader.getContentLength()));
    }

    private static void printLogRequestMessage(HttpRequestLine httpRequestLine, HttpHeader httpHeader, HttpBody httpBody) {
        log.info("\n----------\n{}\n----------",
                httpRequestLine.toString() + httpHeader.toString() + httpBody.toString());
    }
}
