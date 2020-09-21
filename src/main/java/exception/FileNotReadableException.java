package exception;

public class FileNotReadableException extends RuntimeException {
    public FileNotReadableException(String filePath) {
        super("읽을 수 없는 파일입니다! -> " + filePath);
    }
}
