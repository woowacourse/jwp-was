package model;

public enum Status {

    OK(200, "OK", false, true),
    FOUND(302, "FOUND", true, false),
    METHOD_NOT_ALLOWED(405, "METHOD_NOT_ALLOWED", false, false);

    private final int statusCode;
    private final String statusName;
    private final boolean needLocation;
    private final boolean needBody;

    Status(int statusCode, String statusName, boolean needLocation, boolean needBody) {
        this.statusCode = statusCode;
        this.statusName = statusName;
        this.needLocation = needLocation;
        this.needBody = needBody;
    }

    public int getStatusCode() {
        return statusCode;
    }

    public String getStatusName() {
        return statusName;
    }

    public boolean isNeedLocation() {
        return needLocation;
    }

    public boolean isNeedBody() {
        return needBody;
    }
}
