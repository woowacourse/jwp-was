package http;

public enum StatusCode {
    Ok(200), Found(302);

    private final int value;

    StatusCode(int value) {
        this.value = value;
    }
}
