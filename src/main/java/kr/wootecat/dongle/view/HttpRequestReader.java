package kr.wootecat.dongle.view;

import static com.google.common.net.HttpHeaders.*;

import java.io.BufferedReader;
import java.io.IOException;
import java.net.URLDecoder;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import kr.wootecat.dongle.model.http.exception.IllegalRequestDataFormatException;
import kr.wootecat.dongle.model.http.request.HttpRequest;
import kr.wootecat.dongle.model.http.request.HttpRequestBody;
import kr.wootecat.dongle.model.http.request.HttpRequestHeaders;
import kr.wootecat.dongle.model.http.request.HttpRequestLine;
import utils.IOUtils;

public class HttpRequestReader {

    private static final Logger logger = LoggerFactory.getLogger(HttpRequestReader.class);

    private static final String UTF_8_ENCODING_TYPE = "UTF-8";

    private HttpRequestReader() {
    }

    public static HttpRequest from(BufferedReader br) throws IOException {
        try {
            HttpRequestLine line = readRequestLine(br);
            HttpRequestHeaders headers = readRequestHeader(br);
            HttpRequestBody body = readRequestBody(br, headers);
            return new HttpRequest(line, headers, body);
        } catch (IllegalRequestDataFormatException e) {
            logger.error(e.getMessage());
            return HttpRequest.ofInternalError();
        }
    }

    private static HttpRequestLine readRequestLine(BufferedReader br) throws IOException {
        String httpRequestLine = br.readLine();
        httpRequestLine = URLDecoder.decode(httpRequestLine, UTF_8_ENCODING_TYPE);
        return HttpRequestLine.from(httpRequestLine);
    }

    private static HttpRequestHeaders readRequestHeader(BufferedReader br) throws IOException {
        String allHeaders = IOUtils.readAllHeaders(br);
        return HttpRequestHeaders.from(allHeaders);
    }

    private static HttpRequestBody readRequestBody(BufferedReader br, HttpRequestHeaders headers) throws IOException {
        HttpRequestBody body = HttpRequestBody.empty();
        if (headers.containsHeader(CONTENT_LENGTH)) {
            String requestBodyData = IOUtils.readData(br, Integer.parseInt(headers.get(CONTENT_LENGTH)));
            String decodedData = URLDecoder.decode(requestBodyData, UTF_8_ENCODING_TYPE);
            body = HttpRequestBody.from(decodedData);
        }
        return body;
    }
}
