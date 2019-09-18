package webserver.request;

import utils.HttpRequestUtils;
import utils.IOUtils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

public class HttpRequest {
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
        return HttpRequestUtils.generateFilePath(uri.getAbsPath());
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

}
