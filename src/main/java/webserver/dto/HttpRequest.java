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

    public HttpRequest(String httpMethod, String urlPath, Map<String, String> parameters,
        String protocol, Map<String, String> headers, FileNameExtension fileNameExtension) {
        this.httpMethod = HttpMethod.from(httpMethod);
        this.urlPath = UrlPath.from(urlPath);
        this.parameters = Parameters.from(parameters);
        this.protocol = Protocol.of(protocol);
        this.headers = Headers.of(headers);
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
