package webserver.dto;

import java.util.Map;
import webserver.FileNameExtension;

public class HttpRequest {

    private final String httpMethod;
    private final String urlPath;
    private final Map<String, String> parameters;
    private final String protocol;
    private final Map<String, String> headers;
    private final FileNameExtension fileNameExtension;

    public HttpRequest(String HttpMethod, String urlPath, Map<String, String> parameters,
        String protocol, Map<String, String> headers, FileNameExtension fileNameExtension) {
        this.httpMethod = HttpMethod;
        this.urlPath = urlPath;
        this.parameters = parameters;
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

    public Map<String, String> getParameters() {
        return parameters;
    }

    public String getProtocol() {
        return protocol;
    }

    public Map<String, String> getHeaders() {
        return headers;
    }

    public FileNameExtension getFileNameExtension() {
        return fileNameExtension;
    }

    public String getDirectory() {
        return fileNameExtension.getDirectory();
    }

    public boolean isFile() {
        return !fileNameExtension.equals(FileNameExtension.API);
    }
}
