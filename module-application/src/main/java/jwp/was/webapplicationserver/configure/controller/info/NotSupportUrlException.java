package jwp.was.webapplicationserver.configure.controller.info;

public class NotSupportUrlException extends RuntimeException {

    private static final String MESSAGE = "지원하지 않는 URL 입니다.";

    public NotSupportUrlException() {
        super(MESSAGE);
    }
}
