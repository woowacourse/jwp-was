package kr.wootecat.dongle.application.http.exception;

public class ResourceNotFoundException extends RuntimeException {

    private static final String RESOURCE_NOT_EXIST_EXCEPTION_MESSAGE_FORMAT = "요청하는 해당하는 자원이 존재하지 않습니다. : %s";

    public ResourceNotFoundException(String resourceName) {
        super(String.format(RESOURCE_NOT_EXIST_EXCEPTION_MESSAGE_FORMAT, resourceName));
    }
}
