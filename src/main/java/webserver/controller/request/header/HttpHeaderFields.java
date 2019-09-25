package webserver.controller.request.header;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import utils.IOUtils;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.IntStream;

public class HttpHeaderFields {
    private final Map<String, String> headerFields;

    public HttpHeaderFields(Map<String, String> headerFields) {
        this.headerFields = headerFields;
    }

    public String readData(BufferedReader bufferedReader) throws IOException {
        return IOUtils.readData(bufferedReader, getContentsLength());
    }

    public Map<String, String> getHeaderFields() {
        return Collections.unmodifiableMap(headerFields);
    }

    private int getContentsLength() {
        return Integer.parseInt(headerFields.get("Content-Length"));
    }

    public int getContentLength() {
        return Integer.parseInt(headerFields.get("Content-Length"));
    }
}
