package webserver.dto;

import java.util.Map;
import webserver.FileNameExtension;
import webserver.HttpMethod;

public class HttpRequest {

    private final HttpMethod httpMethod;
    private final UrlPath urlPath;
    private final Parameters parameters;
    private final Protocol protocol;
    private final Headers headers;
    private final FileNameExtension fileNameExtension;

    public HttpRequest(HttpMethod httpMethod, UrlPath urlPath, Parameters parameters,
        Protocol protocol, Headers headers, FileNameExtension fileNameExtension) {
        this.httpMethod = httpMethod;
        this.urlPath = urlPath;
        this.parameters = parameters;
        this.protocol = protocol;
        this.headers = headers;
        this.fileNameExtension = fileNameExtension;
    }

    public String getHttpMethod() {
        return httpMethod.name();
    }

    public String getUrlPath() {
        return urlPath.getUrlPath();
    }

    public Map<String, String> getParameters() {
        return parameters.getParameters();
    }

    public String getProtocol() {
        return protocol.getProtocol();
    }

    public Map<String, String> getHeaders() {
        return headers.getHeaders();
    }

    public FileNameExtension getFileNameExtension() {
        return fileNameExtension;
    }

    public String getDirectory() {
        return fileNameExtension.getDirectory();
    }

    public boolean isFile() {
        return !FileNameExtension.API.equals(fileNameExtension);
    }
}
