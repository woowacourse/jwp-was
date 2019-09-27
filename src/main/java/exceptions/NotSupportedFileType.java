package exceptions;

public class NotSupportedFileType extends RuntimeException {
    public NotSupportedFileType(String extension) {
        super("지원하지 않는 파일 종류 입니다.: " + extension);
    }

}
