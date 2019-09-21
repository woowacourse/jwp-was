package utils;

public class FileLoadFailException extends RuntimeException {
    public FileLoadFailException() {
        super("파일을 읽어오는 데 실패했습니다.");
    }
}
