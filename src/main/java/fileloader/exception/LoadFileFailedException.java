package fileloader.exception;

public class LoadFileFailedException extends RuntimeException {
    private static final String LOAD_FILE_FAILED_EXCEPTION_MESSAGE = "파일 로드에 실패했습니다.";

    public LoadFileFailedException(Exception cause) {
        super(LOAD_FILE_FAILED_EXCEPTION_MESSAGE, cause);
    }
}
