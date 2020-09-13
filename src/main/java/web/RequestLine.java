package web;

import java.io.BufferedReader;
import java.io.IOException;

public class RequestLine {

    private String method;
    private String path;
    private String protocol;

    public RequestLine(BufferedReader bufferedReader) throws IOException {
        String url = bufferedReader.readLine();
        String[] splitUrl = url.split(" ");
        this.method = splitUrl[0];
        this.path = splitUrl[1];
        this.protocol = splitUrl[2];
    }

    public String getMethod() {
        return method;
    }

    public String getPath() {
        return path;
    }

    public String getProtocol() {
        return protocol;
    }
}
