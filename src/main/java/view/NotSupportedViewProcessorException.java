package view;

public class NotSupportedViewProcessorException extends RuntimeException {
    public NotSupportedViewProcessorException() {
        super("지원하는 ViewProcessor 가 없습니다.");
    }
}
