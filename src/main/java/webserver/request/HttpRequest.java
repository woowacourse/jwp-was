package webserver.request;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import utils.HttpRequestUtils;
import utils.IOUtils;
import webserver.RequestHandler;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

public class HttpRequest {
    private static final Logger logger = LoggerFactory.getLogger(RequestHandler.class);

    private static final int REQUEST_METHOD_INDEX = 0;
    private static final int REQUEST_URI_INDEX = 1;
    private RequestUri uri;
    private RequestMethod method;
    private RequestHeader header;
    private RequestBody body;

    public HttpRequest(InputStream inputStream) throws IOException {
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream, "UTF-8"));
        parseRequest(bufferedReader.readLine());
        header = new RequestHeader(bufferedReader);
        logger.debug(">>> header : {}", header);
        if (header.getHeader("Content-Length") != null) {
            body = new RequestBody(IOUtils.readData(bufferedReader, Integer.parseInt(header.getHeader("Content-Length"))));
        }
    }

    private void parseRequest(String request) {
        String[] splitRequest = HttpRequestUtils.splitRequest(request);
        method = RequestMethod.valueOf(splitRequest[REQUEST_METHOD_INDEX]);
        uri = new RequestUri(splitRequest[REQUEST_URI_INDEX]);
    }

    public String getAbsPath() {
        return uri.getAbsPath();
    }

    public String getFilePath() {
        if (header.getHeader("Accept").contains("text/html")) {
            return HttpRequestUtils.generateTemplateFilePath(uri.getAbsPath());
        }
        return HttpRequestUtils.generateStaticFilePath(uri.getAbsPath());
    }

    public String getParam(String key) {
        return uri.getQueryString(key);
    }

    public RequestMethod getMethod() {
        return method;
    }

    public String getBody(String key) {
        return body.getBody(key);
    }

    public String getHeader(String key) {
        return header.getHeader(key);
    }
}
