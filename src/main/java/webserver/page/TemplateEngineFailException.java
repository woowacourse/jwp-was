package webserver.page;

public class TemplateEngineFailException extends RuntimeException {
    private TemplateEngineFailException(String s) {
        super(s);
    }

    public static TemplateEngineFailException from(String location, Object obj) {
        return new TemplateEngineFailException(String.format("location: %s, obj: %s", location, obj.toString()));
    }
}
