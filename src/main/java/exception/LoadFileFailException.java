package exception;

public class LoadFileFailException extends RuntimeException {
    private static final String FILE_LOAD_ERROR_MESSAGE = "파일을 읽을 수 없습니다.";

    public LoadFileFailException() {
        super(FILE_LOAD_ERROR_MESSAGE);
    }
}
