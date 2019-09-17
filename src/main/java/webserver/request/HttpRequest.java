package webserver.request;

import utils.HttpRequestUtils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

public class HttpRequest {
    private final RequestUri uri;

    public HttpRequest(InputStream inputStream) throws IOException {
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream, "UTF-8"));
        uri = new RequestUri(bufferedReader.readLine());
    }

    public String getAbsPath(){
        return uri.getAbsPath();
    }

    public String getFilePath() {
        return HttpRequestUtils.generateFilePath(uri.getAbsPath());
    }

    public String getParam(String key) {
        return uri.getQueryString(key);
    }
}
