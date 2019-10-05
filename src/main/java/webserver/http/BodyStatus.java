package webserver.http;

public enum BodyStatus {
    EXISTS(true),
    NOT_EXISTS(false);

    private final boolean isExist;

    BodyStatus(boolean isExist) {
        this.isExist = isExist;
    }

    public boolean isExists() {
        return isExist;
    }
}
