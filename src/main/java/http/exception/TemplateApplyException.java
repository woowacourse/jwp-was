package http.exception;

public class TemplateApplyException extends RuntimeException {
    private static final String ERROR_MESSAGE = "템플릿 적용에 실패했습니다.";

    public TemplateApplyException() {
        super(ERROR_MESSAGE);
    }
}
