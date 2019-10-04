package was.utils;

public class FileLoadFailException extends RuntimeException {
    public FileLoadFailException(String filePath) {
        super(filePath + " 파일을 읽어오는 데 실패했습니다.");
    }
}
