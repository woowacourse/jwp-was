package webserver.http;

public enum HttpMethod {
    GET(BodyStatus.NOT_EXISTS),
    POST(BodyStatus.EXISTS),
    PUT(BodyStatus.EXISTS),
    DELETE(BodyStatus.NOT_EXISTS),
    HEAD(BodyStatus.NOT_EXISTS),
    CONNECT(BodyStatus.NOT_EXISTS),
    OPTION(BodyStatus.NOT_EXISTS),
    TRACE(BodyStatus.NOT_EXISTS),
    PATCH(BodyStatus.EXISTS);

    private final BodyStatus bodyStatus;

    HttpMethod(BodyStatus bodyStatus) {
        this.bodyStatus = bodyStatus;
    }

    public boolean isBodyExists() {
        return bodyStatus.isExists();
    }
}
