package http.request;

import http.HTTP;
import http.RequestMethod;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;

public class HttpRequest implements AutoCloseable {
    private static final Logger logger = LoggerFactory.getLogger(HttpRequest.class);

    private RequestFirstLine requestFirstLine;
    private RequestHeader requestHeader;
    private RequestBody requestBody;

    private BufferedReader bufferedReader;

    public HttpRequest(InputStream in) throws IOException {
        bufferedReader = new BufferedReader(new InputStreamReader(in, StandardCharsets.UTF_8));
        this.requestFirstLine = new RequestFirstLine(bufferedReader.readLine());

        this.requestHeader = new RequestHeader(bufferedReader);

        if (requestHeader.contains(HTTP.CONTENT_LENGTH)) {
            this.requestBody = new RequestBody(bufferedReader,
                    Integer.parseInt(requestHeader.getHeaderContents(HTTP.CONTENT_LENGTH.getPhrase())));
        }
    }

    public RequestMethod getMethod() {
        return requestFirstLine.getMethod();
    }

    public String getPath() {
        return requestFirstLine.getPath();
    }

    public String getQueryString() {
        return decode(requestFirstLine.getQuery());
    }

    public String getBody() {
        if (requestBody == null) {
            return "";
        }
        return decode(requestBody.getBody());
    }

    private String decode(String str) {
        try {
            return URLDecoder.decode(str, StandardCharsets.UTF_8.toString());
        } catch (UnsupportedEncodingException e) {
            throw new RuntimeException("UTF-8 not supported");
        }
    }

    @Override
    public void close() throws IOException {
        bufferedReader.close();
    }
}
