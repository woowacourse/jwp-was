package http.response;

public class HttpResponseEntity {
    private String viewTemplatePath;
    private HttpStatus status;

    public HttpResponseEntity(String viewTemplatePath, HttpStatus status) {
        this.viewTemplatePath = viewTemplatePath;
        this.status = status;
    }

    public String getViewTemplatePath() {
        return viewTemplatePath;
    }

    public HttpStatus getStatus() {
        return status;
    }
}
