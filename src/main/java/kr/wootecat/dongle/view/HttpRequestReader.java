package kr.wootecat.dongle.view;

import static com.google.common.net.HttpHeaders.*;

import java.io.BufferedReader;
import java.io.IOException;
import java.net.URLDecoder;

import kr.wootecat.dongle.model.http.request.HttpRequest;
import kr.wootecat.dongle.model.http.request.HttpRequestBody;
import kr.wootecat.dongle.model.http.request.HttpRequestHeaders;
import kr.wootecat.dongle.model.http.request.HttpRequestLine;
import utils.IOUtils;

public class HttpRequestReader {

    private static final String UTF_8_ENCODING_TYPE = "UTF-8";

    private HttpRequestReader() {
    }

    public static HttpRequest parse(BufferedReader br) throws IOException {
        HttpRequestLine line = parseRequestLine(br);
        HttpRequestHeaders headers = parseRequestHeader(br);
        HttpRequestBody body = parseRequestBody(br, headers);
        return new HttpRequest(line, headers, body);
    }

    private static HttpRequestLine parseRequestLine(BufferedReader br) throws IOException {
        String httpRequestLine = br.readLine();
        httpRequestLine = URLDecoder.decode(httpRequestLine, UTF_8_ENCODING_TYPE);
        return HttpRequestLine.from(httpRequestLine);
    }

    private static HttpRequestHeaders parseRequestHeader(BufferedReader br) throws IOException {
        String allHeaders = IOUtils.readAllHeaders(br);
        return HttpRequestHeaders.from(allHeaders);
    }

    private static HttpRequestBody parseRequestBody(BufferedReader br, HttpRequestHeaders headers) throws IOException {
        HttpRequestBody body = HttpRequestBody.empty();
        if (headers.containsHeader(CONTENT_LENGTH)) {
            String requestBodyData = IOUtils.readData(br, Integer.parseInt(headers.get(CONTENT_LENGTH)));
            String decodedData = URLDecoder.decode(requestBodyData, UTF_8_ENCODING_TYPE);
            body = HttpRequestBody.from(decodedData);
        }
        return body;
    }
}
