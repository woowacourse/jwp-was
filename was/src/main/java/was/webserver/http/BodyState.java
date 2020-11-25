package was.webserver.http;

public enum BodyState {
    EMPTY, NOT_EMPTY;

    public boolean isEmpty() {
        return this.equals(EMPTY);
    }
}
