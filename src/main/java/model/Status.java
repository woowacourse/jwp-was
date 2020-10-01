package model;

public enum Status {
    OK(200, "OK", false),
    FOUND(302, "FOUND", true),
    METHOD_NOT_ALLOWED(405, "METHOD_NOT_ALLOWED", false);

    private final int statusCode;
    private final String statusName;
    private final boolean needLocation;

    Status(int statusCode, String statusName, boolean needLocation) {
        this.statusCode = statusCode;
        this.statusName = statusName;
        this.needLocation = needLocation;
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
}
