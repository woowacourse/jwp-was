package webserver.dto;

import java.util.Map;
import webserver.FileNameExtension;
import webserver.Kind;

public class HttpRequest {

    private String httpMethod;
    private String urlPath;
    private String protocol;
    private Map<String, String> headers;
    private FileNameExtension fileNameExtension;

    public HttpRequest(String HttpMethod, String urlPath, String protocol,
        Map<String, String> headers, FileNameExtension fileNameExtension) {
        this.httpMethod = HttpMethod;
        this.urlPath = urlPath;
        this.protocol = protocol;
        this.headers = headers;
        this.fileNameExtension = fileNameExtension;
    }

    public String getHttpMethod() {
        return httpMethod;
    }

    public String getUrlPath() {
        return urlPath;
    }

    public String getProtocol() {
        return protocol;
    }

    public Map<String, String> getHeaders() {
        return headers;
    }

    public Kind getKind() {
        return fileNameExtension.getKind();
    }

    public FileNameExtension getFileNameExtension() {
        return fileNameExtension;
    }

    public boolean isFile() {
        return !fileNameExtension.getKind().equals(Kind.API);
    }
}
