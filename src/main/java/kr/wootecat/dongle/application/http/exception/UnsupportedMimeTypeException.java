package kr.wootecat.dongle.application.http.exception;

public class UnsupportedMimeTypeException extends RuntimeException {

    private static final String UNSUPPORTED_MIME_TYPE_EXCEPTION_MESSAGE_FORMAT = "해당 하는 요청에 대한 리소스형식은 지원하지 않습니다. : %s";

    public UnsupportedMimeTypeException(String extensionType) {
        super(String.format(UNSUPPORTED_MIME_TYPE_EXCEPTION_MESSAGE_FORMAT, extensionType));
    }
}
