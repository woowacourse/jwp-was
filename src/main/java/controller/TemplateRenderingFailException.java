package controller;

public class TemplateRenderingFailException extends RuntimeException {
    private static final String MESSAGE = "Rendering에 실패하였습니다.";

    public TemplateRenderingFailException() {
        super(MESSAGE);
    }
}
