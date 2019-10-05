package webserver.exception;

public class FailedRenderingException extends RuntimeException {
    public FailedRenderingException() {
        super("해당 파일을 반환하는데 실패했습니다.");
    }
}
