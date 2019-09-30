package mvc.view;

public class TemplateEngineParsingException extends RuntimeException {
    public TemplateEngineParsingException() {
        super("템플릿 엔진이 템플릿을 파싱하는 데 실패했습니다.");
    }
}
