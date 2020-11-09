package exception;

public enum CustomExceptionStatus {
    FILE_NOT_EXIST("파일이 존재하지 않습니다.");

    private final String message;

    CustomExceptionStatus(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }
}
