package http.response;

public enum StatusCode {
    OK(200), FOUND(302);

    private final int value;

    StatusCode(int value) {
        this.value = value;
    }

    public int getStatusValue() {
        return value;
    }
}
