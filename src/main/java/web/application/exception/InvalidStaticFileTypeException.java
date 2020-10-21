package web.application.exception;

public class InvalidStaticFileTypeException extends IllegalArgumentException {

    private static final String INVALID_EXTENSION_FILE_TYPE_MESSAGE = "찾을 수 없는 파일타입 입니다 . ExtensionType = ";

    public InvalidStaticFileTypeException(String extensionType) {
        super(INVALID_EXTENSION_FILE_TYPE_MESSAGE + extensionType);
    }
}
