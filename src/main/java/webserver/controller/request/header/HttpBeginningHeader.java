package webserver.controller.request.header;

import utils.FileIoUtils;
import webserver.controller.response.ContentType;

import java.io.BufferedReader;
import java.io.IOException;
import java.net.URISyntaxException;

public class HttpBeginningHeader {
    private static final String STATIC_FILE_PATH = "./static";
    private static final String NON_STATIC_FILE_PATH = "./templates";
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

    public byte[] getResponseBody(ContentType contentType) throws IOException, URISyntaxException {
        if(contentType == ContentType.HTML || contentType == ContentType.ICO) {
         return FileIoUtils.loadFileFromClasspath(NON_STATIC_FILE_PATH + url);
        }
        return FileIoUtils.loadFileFromClasspath(STATIC_FILE_PATH + url);
    }

    public ContentType getContentType() {
        return ContentType.match(url);
    }
}
