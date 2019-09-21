package request;

import request.header.HttpBeginningHeader;
import request.header.HttpHeaderFields;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

public class HttpRequest {
    private final BufferedReader bufferedReader;
    private final HttpBeginningHeader httpBeginningHeader;
    private final HttpHeaderFields httpHeaderFileds;

    public HttpRequest(final InputStream inputStream) throws IOException {
        this.bufferedReader = new BufferedReader(new InputStreamReader(inputStream, "UTF-8"));
        this.httpBeginningHeader = new HttpBeginningHeader(bufferedReader);
        this.httpHeaderFileds = new HttpHeaderFields();
        httpHeaderFileds.save(bufferedReader);
    }
}
