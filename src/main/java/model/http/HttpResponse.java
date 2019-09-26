package model.http;

import utils.HttpStatus;
import utils.MediaType;

import java.util.HashMap;
import java.util.Map;

public class HttpResponse {
    private static final String ERROR_PAGE = "/error.html";
    // TODO model 맵의 역할
    private Map<String, String> body = new HashMap<>();
    private String path;
    private HttpStatus httpStatus;
    private MediaType mediaType;
    private String errorMessage;

    private HttpResponse() {
        this.httpStatus = HttpStatus.DEFAULT;
    }

    public static HttpResponse of() {
        return new HttpResponse();
    }

    public static HttpResponse createErrorResponse() {
        HttpResponse httpResponse = new HttpResponse();
        httpResponse.path = ViewLocation.TEMPLATE + ERROR_PAGE;
        httpResponse.httpStatus = HttpStatus.NOT_FOUND;
        httpResponse.mediaType = MediaType.HTML;
        return httpResponse;
    }

    public void sendRedirect(String path, HttpStatus httpStatus) {
        this.path = path;
        this.httpStatus = httpStatus;
        this.mediaType = MediaType.find(path.substring(path.lastIndexOf(".") + 1).toUpperCase());
    }

    public void sendError(HttpStatus httpStatus, String msg) {
        this.httpStatus = httpStatus;
        this.errorMessage = msg;
    }

    public boolean hasError() {
        return httpStatus.isError();
    }

    public String getPath() {
        return path;
    }

    public int getHttpStatusCode() {
        return httpStatus.getValue();
    }

    public String getHttpReasonPhrase() {
        return httpStatus.getReasonPhrase();
    }

    public String getMediaType() {
        return mediaType.getContentType();
    }

    public String getResourceName() {
        return path.substring(path.lastIndexOf("/"));
    }

    public boolean isRedirect() {
        return httpStatus == HttpStatus.REDIRECT;
    }

    public boolean isNotInitialized() {
        return httpStatus.isNotInitialized();
    }

    public HttpStatus getHttpStatus() {
        return httpStatus;
    }
}
