package request.header;

import java.io.BufferedReader;
import java.io.IOException;

public class HttpBeginningHeader {
    private final HttpMethod httpMethod;
    private final String url;
    private final String version;

    public HttpBeginningHeader(BufferedReader bufferedReader) throws IOException {
        String beginningHeaderLine = bufferedReader.readLine();
        String[] splitedBeginningHeaderLine = beginningHeaderLine.split(" ");
        this.httpMethod = HttpMethod.valueOf(splitedBeginningHeaderLine[0]);
        this.url = splitedBeginningHeaderLine[1];
        this.version = splitedBeginningHeaderLine[2];
    }

    public HttpMethod getHttpMethod() {
        return httpMethod;
    }

    public String getUrl() {
        return url;
    }

    public String getVersion() {
        return version;
    }
}
