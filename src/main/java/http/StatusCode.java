package http;

public enum StatusCode {
    OK(200), Found(302);

    private final int value;

    StatusCode(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }
}
