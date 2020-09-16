package exception;

public class FileNotSupportedException extends RuntimeException {
    public FileNotSupportedException() {
        super("지원하지 않는 파일 형식입니다.");
    }
}
