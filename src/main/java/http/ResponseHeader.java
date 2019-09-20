package http;

public class ResponseHeader {
    private static final int OK_STATUS_CODE = 200;

    private final int statusCode;
    private final String type;
    private String location;
    private String url;

    public ResponseHeader(int statusCode, String type, String location, String url) {
        this.statusCode = statusCode;
        this.type = String.format("text/%s", type);
        this.location = location;
        this.url = url;
    }

    public String getType() {
        return type;
    }

    public String getLocation() {
        return location;
    }

    public boolean isOk() {
        return (statusCode == OK_STATUS_CODE);
    }

    public String getUrl() {
        return isOk() ? url : location;
    }
}
