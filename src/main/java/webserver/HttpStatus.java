package webserver;

public enum HttpStatus {
    OK(200),
    FOUND(300);

    private final int number;

    HttpStatus(int number) {
        this.number = number;
    }

    public int getNumber() {
        return number;
    }
}
