package webserver.http.session;

public enum HttpSessionStatus {
    VALID, INVALID;

    public boolean isValid() {
        return this.equals(HttpSessionStatus.VALID);
    }
}
