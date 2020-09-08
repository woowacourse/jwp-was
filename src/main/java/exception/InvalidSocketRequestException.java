package exception;

public class InvalidSocketRequestException extends RuntimeException {

    public InvalidSocketRequestException() {
        super("잘못된 소켓 요청입니다.");
    }
}
