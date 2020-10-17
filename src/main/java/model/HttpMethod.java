package model;

public enum HttpMethod {
    GET(new HttpGetService());

    private final HttpService service;

    HttpMethod(HttpService service) {
        this.service = service;
    }

    public HttpService getService() {
        return service;
    }
}
