package webserver.request;

import utils.HttpRequestUtils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

public class HttpRequest {
    private final String url;

    public HttpRequest(InputStream inputStream) throws IOException {
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream, "UTF-8"));
        url = HttpRequestUtils.generateFilePath(bufferedReader.readLine());
    }

    public String getPath() {
        return url;
    }
}
