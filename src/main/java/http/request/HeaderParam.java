package http.request;

public enum HeaderParam {
    HOST("host"),
    CONNECTION("connection"),
    CONTENT_LENGTH("content-length"),
    CONTENT_TYPE("content-type"),
    ACCEPT("accept");

    private final String paramName;

    HeaderParam(String paramName) {
        this.paramName = paramName;
    }

    public String getParamName() {
        return paramName;
    }
}
